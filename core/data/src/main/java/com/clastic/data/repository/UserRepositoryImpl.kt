package com.clastic.data.repository

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.clastic.data.R
import com.clastic.domain.repository.UserRepository
import com.clastic.model.OwnedReward
import com.clastic.model.authentication.AuthenticationResult
import com.clastic.model.User
import com.clastic.model.authentication.AuthUser
import com.clastic.utils.TimeUtil
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val appContext: Context,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : UserRepository {
    private val oneTapClient = Identity.getSignInClient(appContext)

    override fun userGoogleSignIn(
        onIntentSenderReceived: (IntentSender?) -> Unit
    ) {
        oneTapClient.beginSignIn(
            buildSignInRequest()
        ).addOnSuccessListener { result ->
            onIntentSenderReceived(result.pendingIntent.intentSender)
        }.addOnFailureListener { e ->
            e.printStackTrace()
            onIntentSenderReceived(null)
        }
    }

    override fun getGoogleSignInResultFromIntent(
        intent: Intent,
        onSignInResult: (AuthenticationResult) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

        auth.signInWithCredential(googleCredentials)
            .addOnSuccessListener { authResult ->
                val user = authResult.user
                db.collection("user")
                    .document(user?.email ?: "")
                    .get()
                    .addOnCompleteListener { task ->
                        val documentSnapshot = task.result
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            createLoginResultSuccess(
                                user = user,
                                onResultSuccess = onSignInResult,
                                onResultFailed = onFetchFailed
                            )
                        } else {
                            createRegisterResultSuccess(
                                user = user,
                                name = user?.displayName ?: user?.email ?: "user",
                                onResultSuccess = onSignInResult,
                                onResultFailed = onFetchFailed
                            )
                        }
                    }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                val result = AuthenticationResult(
                    data = null,
                    errorMessage = e.message
                )
                onSignInResult(result)
            }
    }

    override fun userSignOut(
        onSignOutSuccess: () -> Unit,
        onSignOutFailed: (String) -> Unit
    ) {
        oneTapClient.signOut()
            .addOnSuccessListener {
                auth.signOut()
                onSignOutSuccess()
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                onSignOutFailed(e.message.toString())
            }
    }

    override fun registerWithEmail(
        name: String,
        email: String,
        password: String,
        onResultSuccess: (AuthenticationResult) -> Unit,
        onResultFailed: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { task ->
                createRegisterResultSuccess(
                    user = task.user,
                    name = name,
                    onResultSuccess = onResultSuccess,
                    onResultFailed = onResultFailed
                )
            }
            .addOnFailureListener { e ->
                onResultFailed(e.message ?: "")
            }
    }

    override fun loginWithEmail(
        email: String,
        password: String,
        onResultSuccess: (AuthenticationResult) -> Unit,
        onResultFailed: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { task ->
                createLoginResultSuccess(
                    user = task.user,
                    onResultSuccess = onResultSuccess,
                    onResultFailed = onResultFailed
                )
            }
            .addOnFailureListener { e ->
                onResultFailed(e.message ?: "")
            }
    }

    override fun getLoggedInUser(): AuthUser? {
        return auth.currentUser?.run {
            AuthUser(
                userId = uid,
                username = displayName,
                email = email ?: "",
                userImage = photoUrl?.toString(),
                token = getIdToken(false).toString()
            )
        }
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(appContext.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    @Suppress("UNCHECKED_CAST")
    private fun createLoginResultSuccess(
        user: FirebaseUser?,
        onResultSuccess: (AuthenticationResult) -> Unit,
        onResultFailed: (String) -> Unit
    ) {
        if (user != null) {
            db.collection("user")
                .document(user.email ?: "")
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val result = AuthenticationResult(
                            data = user.run {
                                User(
                                    userId = uid,
                                    username = document.getString("username"),
                                    email = email ?: "",
                                    points = document.getLong("points")?.toInt() ?: 0,
                                    userPhoto = document.getString("userPhoto"),
                                    level = document.getLong("level")?.toInt() ?: 0,
                                    exp = document.getLong("exp")?.toInt() ?: 0,
                                    createdAt = document.getTimestamp("createdAt")?.seconds ?: 0,
                                    role = document.getString("role") ?: "user",
                                    totalPlastic = document.getDouble("totalPlastic") ?: 0.0,
                                    totalTransaction = document.getLong("totalTransaction")?.toInt() ?: 0,
                                    plasticTransactionList = document.get("plasticTransactionList") as List<String>,
                                    rewardList = getRewardListFromSnapShot(document),
                                    rewardTransactionList = document.get("rewardTransactionList") as List<String>
                                )
                            },
                            errorMessage = null
                        )
                        onResultSuccess(result)
                    }
                }
                .addOnFailureListener { exception -> onResultFailed(exception.message ?: "") }
        }
    }

    private fun createRegisterResultSuccess(
        user: FirebaseUser?,
        name: String,
        onResultSuccess: (AuthenticationResult) -> Unit,
        onResultFailed: (String) -> Unit,
    ) {
        user?.run {
            val newUser = hashMapOf(
                "userId" to uid,
                "username" to name,
                "email" to email,
                "userPhoto" to photoUrl?.toString(),
                "points" to 0,
                "createdAt" to TimeUtil.getTimestamp(),
                "level" to 1,
                "exp" to 0,
                "role" to "user",
                "totalPlastic" to 0.0,
                "totalTransaction" to 0,
                "plasticTransactionList" to emptyList<String>(),
                "rewardList" to emptyList<Map<String, Any>>(),
                "rewardTransactionList" to emptyList<String>()
            )
            db.collection("user")
                .document(email ?: "")
                .set(newUser)
                .addOnSuccessListener {
                    onResultSuccess(
                        AuthenticationResult(
                            data = user.run {
                                User(
                                    userId = uid,
                                    username = name,
                                    email = email ?: "",
                                    userPhoto = photoUrl?.toString(),
                                    points = 0,
                                    createdAt = TimeUtil.getCurrentTimeSeconds(),
                                    level = 1,
                                    exp = 0,
                                    role = "user",
                                    totalPlastic = 0.0,
                                    totalTransaction = 0,
                                    plasticTransactionList = emptyList(),
                                    rewardList = emptyList(),
                                    rewardTransactionList = emptyList()
                                )
                            },
                            errorMessage = null
                        )
                    )
                }
                .addOnFailureListener { error -> onResultFailed(error.message ?: "") }
        }
    }

    override fun getUserInfo(
        onFetchSuccess: (User) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        db.collection("user")
            .document(auth.currentUser?.email ?: "")
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    onFetchSuccess(getUserFromSnapshot(document))
                }
            }
            .addOnFailureListener { e ->
                onFetchFailed(e.message.toString())
            }
    }

    override fun checkUserById(
        userId: String,
        onFetchSuccess: (Boolean, user: User?) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        db.collection("user").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    onFetchSuccess(document.exists(), getUserFromSnapshot(document))
                } else {
                    onFetchSuccess(false, null)
                }
            }
            .addOnFailureListener { error ->
                onFetchFailed(error.message.toString())
            }
    }

    override fun fetchAllUsers(
        onFetchSuccess: (List<User>) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        db.collection("user").get()
            .addOnSuccessListener { result ->
                val users = result.map { document -> getUserFromSnapshot(document) }
                onFetchSuccess(users)
            }
            .addOnFailureListener { error -> onFetchFailed(error.message.toString()) }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getRewardListFromSnapShot(document: DocumentSnapshot): List<OwnedReward> {
        val rewardList = mutableListOf<OwnedReward>()
        val rewardListData = document.get("rewardList") as List<Map<String, *>>
        rewardListData.forEach { data ->
            val id = data["id"] as String
            val count = data["count"] as Long
            rewardList.add(
                OwnedReward(
                    rewardId = id,
                    count = count.toInt()
                )
            )
        }
        return rewardList
    }

    private fun getUserFromSnapshot(document: DocumentSnapshot): User {
        @Suppress("UNCHECKED_CAST")
        return User(
            userId = document.getString("userId") ?: "",
            username = document.getString("username"),
            email = document.getString("email") ?: "",
            points = document.getLong("points")?.toInt() ?: 0,
            userPhoto = document.getString("userPhoto"),
            level = document.getLong("level")?.toInt() ?: 0,
            exp = document.getLong("exp")?.toInt() ?: 0,
            createdAt = document.getTimestamp("createdAt")?.seconds ?: 0,
            role = document.getString("role") ?: "user",
            totalPlastic = document.getDouble("totalPlastic") ?: 0.0,
            totalTransaction = document.getLong("totalTransaction")?.toInt() ?: 0,
            plasticTransactionList = document.get("plasticTransactionList") as List<String>,
            rewardList = getRewardListFromSnapShot(document),
            rewardTransactionList = document.get("rewardTransactionList") as List<String>
       )
    }
}