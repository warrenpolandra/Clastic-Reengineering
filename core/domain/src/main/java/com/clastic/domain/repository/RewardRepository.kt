package com.clastic.domain.repository

import com.clastic.model.Reward

interface RewardRepository {
    fun fetchRewardList(
        onFetchSuccess: (List<Reward>) -> Unit,
        onFetchFailed: (error: String) -> Unit
    )

    fun fetchRewardById(
        rewardId: String,
        onFetchSuccess: (Reward) -> Unit,
        onFetchFailed: (error: String) -> Unit
    )
}