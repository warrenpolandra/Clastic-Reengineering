package com.clastic.model

import androidx.annotation.Keep

@Keep
data class Reward(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val imagePath: String = "",
    val value: Int = 0,
    val termsAndConditions: List<String> = emptyList()
)

@Keep
data class OwnedReward(
    val id: String,
    val rewardId: String
)