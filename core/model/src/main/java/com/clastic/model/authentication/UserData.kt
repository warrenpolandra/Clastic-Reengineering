package com.clastic.model.authentication

import androidx.annotation.Keep

@Keep
data class UserData(
    val userId: String,
    val username: String?,
    val email: String,
    val points: Int,
    val userPhoto: String?,
    val level: Int,
    val exp: Int,
    val createdAt: String,
    val role: String
)