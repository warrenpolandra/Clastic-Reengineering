package com.clastic.model.authentication

data class UserData(
    val userId: String,
    val username: String?,
    val userImage: String?,
    val token: String?
)