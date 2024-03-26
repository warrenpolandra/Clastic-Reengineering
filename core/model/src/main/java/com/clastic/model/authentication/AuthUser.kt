package com.clastic.model.authentication

data class AuthUser(
    val userId: String,
    val username: String?,
    val userImage: String?,
    val token: String?
)
