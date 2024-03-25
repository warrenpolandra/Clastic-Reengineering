package com.clastic.domain.repository

import android.content.Intent
import android.content.IntentSender
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
}