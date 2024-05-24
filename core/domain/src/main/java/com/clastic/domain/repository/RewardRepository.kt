package com.clastic.domain.repository

import com.clastic.model.OrderedReward
import com.clastic.model.Reward
import com.clastic.model.RewardTransaction
import kotlinx.coroutines.flow.Flow

interface RewardRepository {
    fun fetchRewardList(
        onFetchSuccess: (List<Reward>) -> Unit,
        onFetchFailed: (error: String) -> Unit
    )

    fun getRewardById(rewardId: String): OrderedReward

    fun updateRewardCount(
        rewardId: String,
        count: Int
    )

    fun getAllRewards(): Flow<List<OrderedReward>>

    fun createRewardTransaction(
        orderedRewardList: List<OrderedReward>,
        totalPoints: Int,
        userId: String,
        onPostSuccess: (transactionId: String) -> Unit,
        onPostFailed: (error: String) -> Unit
    )

    fun fetchRewardTransactionById(
        transactionId: String,
        onFetchSuccess: (RewardTransaction) -> Unit,
        onFetchFailed: (error: String) -> Unit
    )

    fun fetchRewardTransactionByUserId(
        userId: String,
        onFetchSuccess: (List<RewardTransaction>) -> Unit,
        onFetchFailed: (error: String) -> Unit
    )
}