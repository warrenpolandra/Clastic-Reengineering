package com.clastic.model.authentication

import com.clastic.model.User

class AuthenticationResult(
    val data: User?,
    val errorMessage: String?
)