package com.clastic.model.authentication

import androidx.annotation.Keep

@Keep
class AuthenticationResult(
    val data: UserData?,
    val errorMessage: String?
)