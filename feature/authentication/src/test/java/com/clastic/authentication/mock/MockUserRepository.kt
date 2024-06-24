package com.clastic.authentication.mock

import android.content.Intent
import android.content.IntentSender
import com.clastic.domain.repository.UserRepository
import com.clastic.model.User
import com.clastic.model.authentication.AuthUser
import com.clastic.model.authentication.AuthenticationResult

class MockUserRepository(
    private val errorMessage: String,
    private val isSuccess: Boolean
): UserRepository {
    override fun userGoogleSignIn(onIntentSenderReceived: (IntentSender?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getGoogleSignInResultFromIntent(
        intent: Intent,
        onSignInResult: (AuthenticationResult) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun userSignOut(onSignOutSuccess: () -> Unit, onSignOutFailed: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun registerWithEmail(
        name: String,
        email: String,
        password: String,
        onResultSuccess: (AuthenticationResult) -> Unit,
        onResultFailed: (String) -> Unit
    ) {
        if (isSuccess) {
            onResultSuccess(
                AuthenticationResult(
                    User(
                        userId = "SAMPLE_USER_ID",
                        username = name,
                        email = email,
                        userPhoto = "",
                        points = 0,
                        createdAt = 0L,
                        level = 1,
                        exp = 0,
                        role = "user",
                        totalPlastic = 0.0,
                        totalTransaction = 0,
                        plasticTransactionList = emptyList(),
                        rewardList = emptyList(),
                        rewardTransactionList = emptyList(),
                        missionSubmissionList = emptyList()
                    ),
                    null
                )
            )
        } else {
            onResultFailed(errorMessage)
        }
    }

    override fun loginWithEmail(
        email: String,
        password: String,
        onResultSuccess: (AuthenticationResult) -> Unit,
        onResultFailed: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getLoggedInUser(): AuthUser? {
        TODO("Not yet implemented")
    }

    override fun getUserInfo(onFetchSuccess: (User) -> Unit, onFetchFailed: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun checkUserById(
        userId: String,
        onFetchSuccess: (Boolean, user: User?) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun fetchAllUsers(
        onFetchSuccess: (List<User>) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}