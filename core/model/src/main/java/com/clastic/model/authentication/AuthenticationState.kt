package com.clastic.model.authentication

import androidx.annotation.Keep

@Keep
data class AuthenticationState(
    val isAuthSuccessful: Boolean = false,
    val authError: String? = null
)