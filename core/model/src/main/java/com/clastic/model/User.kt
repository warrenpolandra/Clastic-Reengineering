package com.clastic.model

import androidx.annotation.Keep

@Keep
data class User(
    val userId: String,
    val username: String?,
    val email: String,
    val points: Int,
    val userPhoto: String?,
    val level: Int,
    val createdAt: String,
    val role: String
)