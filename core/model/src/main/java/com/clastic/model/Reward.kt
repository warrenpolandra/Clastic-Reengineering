package com.clastic.model

import androidx.annotation.Keep
import com.google.firebase.Timestamp

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
data class OrderedReward(
    val reward: Reward,
    val count: Int
)

@Keep
data class OwnedReward(
    val rewardId: String,
    val count: Int,
)

@Keep
data class RewardTransaction(
    val id: String = "",
    val time: Timestamp = Timestamp.now(),
    val rewardList: List<OwnedReward> = emptyList(),
    val totalPoints: Long = 0L,
    val userId: String = ""
)