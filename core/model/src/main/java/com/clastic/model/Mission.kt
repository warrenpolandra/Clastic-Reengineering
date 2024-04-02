package com.clastic.model

import androidx.annotation.Keep

@Keep
data class Mission(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val objectives: List<String> = emptyList(),
    val imageUrl: String = "",
    val tags: List<String> = emptyList(),
    val reward: Int = 0,
    val impacts: List<Impact> = emptyList(),
    val endDate: Long = 0L
)