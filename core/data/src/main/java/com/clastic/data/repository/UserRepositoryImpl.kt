package com.clastic.data.repository

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.clastic.data.R
import com.clastic.data.Utils
import com.clastic.domain.repository.UserRepository
import com.clastic.model.authentication.AuthenticationResult
import com.clastic.model.User
import com.clastic.model.authentication.AuthUser
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val appContext: Context
) : UserRepository {

    private val auth = Firebase.auth
    private val db = FirebaseFirestore.getInstance()
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
        onSignOutFailed: (Exception) -> Unit
    ) {
        oneTapClient.signOut()
            .addOnSuccessListener {
                auth.signOut()
                onSignOutSuccess()
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                onSignOutFailed(e)
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
                                    createdAt = document.getTimestamp("createdAt").toString(),
                                    role = document.getString("role") ?: "user"
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
                "userPhoto" to photoUrl?.toString(),
                "points" to 0,
                // Todo: change Any to Prize
                "prizes" to emptyList<Map<Long, Any>>(),
                "createdAt" to Utils.getTimestamp(),
                "level" to 1,
                "exp" to 0,
                "role" to "user"
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
                                    email = email!!,
                                    userPhoto = photoUrl?.toString(),
                                    points = 0,
                                    createdAt = Utils.getTimestamp().toString(),
                                    level = 1,
                                    exp = 0,
                                    role = "user"
                                )
                            },
                            errorMessage = null
                        )
                    )
                }
                .addOnFailureListener { error -> onResultFailed(error.message ?: "") }
        }
    }
}