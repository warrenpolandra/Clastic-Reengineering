package com.clastic.model

import androidx.annotation.Keep

@Keep
data class Reward(
    val id: String,
    val name: String,
    val imagePath: String,
    val value: Int,
    val termsAndConditions: List<String>
)

@Keep
data class OwnedReward(
    val id: String,
    val rewardId: String
)