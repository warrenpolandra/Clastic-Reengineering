package com.clastic.model.authentication

data class AuthenticationState(
    val isAuthSuccessful: Boolean = false,
    val authError: String? = null
)