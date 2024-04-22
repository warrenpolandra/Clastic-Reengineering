package com.clastic.domain.repository

import android.content.Intent
import android.content.IntentSender
import com.clastic.model.User
import com.clastic.model.authentication.AuthUser
import com.clastic.model.authentication.AuthenticationResult

interface UserRepository {
    fun userGoogleSignIn(
        onIntentSenderReceived: (IntentSender?) -> Unit
    )

    fun getGoogleSignInResultFromIntent(
        intent: Intent,
        onSignInResult: (AuthenticationResult) -> Unit,
        onFetchFailed: (String) -> Unit
    )

    fun userSignOut(
        onSignOutSuccess: () -> Unit,
        onSignOutFailed: (Exception) -> Unit
    )

    fun registerWithEmail(
        name: String,
        email: String,
        password: String,
        onResultSuccess: (AuthenticationResult) -> Unit,
        onResultFailed: (String) -> Unit
    )

    fun loginWithEmail(
        email: String,
        password: String,
        onResultSuccess: (AuthenticationResult) -> Unit,
        onResultFailed: (String) -> Unit
    )

    fun getLoggedInUser(): AuthUser?

    fun getUserInfo(
        onFetchSuccess: (User) -> Unit,
        onFetchFailed: (String) -> Unit
    )

    fun checkUserById(
        userId: String,
        onFetchSuccess: (Boolean, user: User?) -> Unit,
        onFetchFailed: (String) -> Unit
    )
}