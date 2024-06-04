package com.clastic.model

import androidx.annotation.Keep
import com.google.firebase.Timestamp

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

@Keep
data class MissionTransaction(
    val id: String,
    val missionId: String,
    val submissionUrl: String,
    val time: Timestamp,
    val totalPoints: Int,
    val userId: String,
    val isPicture: Boolean
)