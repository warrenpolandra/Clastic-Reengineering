package com.clastic.model

import androidx.annotation.Keep

@Keep
data class User(
    val userId: String = "",
    val username: String? = "",
    val email: String = "",
    val points: Int = 0,
    val userPhoto: String? = "",
    val level: Int = 0,
    val exp: Int = 0,
    val createdAt: Long = 0,
    val role: String = "",
    val totalPlastic: Double = 0.0,
    val totalTransaction: Int = 0,
    val plasticTransactionList: List<String> = emptyList()
)