package com.clastic.model

import androidx.annotation.Keep
import com.google.firebase.Timestamp

@Keep
data class Mission(
    val id: String,
    val title: String,
    val description: String,
    val objectives: List<String>,
    val imageUrl: String,
    val tags: List<String>,
    val reward: Int,
    val impacts: List<Impact>,
    val endDate: Timestamp
)