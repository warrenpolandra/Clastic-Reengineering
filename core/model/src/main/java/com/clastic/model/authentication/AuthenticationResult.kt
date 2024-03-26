package com.clastic.model.authentication

import androidx.annotation.Keep
import com.clastic.model.User

@Keep
class AuthenticationResult(
    val data: User?,
    val errorMessage: String?
)