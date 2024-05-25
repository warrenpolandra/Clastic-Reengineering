package com.clastic.data.repository

import com.clastic.domain.repository.RewardRepository
import com.clastic.model.OrderedReward
import com.clastic.model.OwnedReward
import com.clastic.model.Reward
import com.clastic.model.RewardTransaction
import com.clastic.utils.TimeUtil
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class RewardRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
): RewardRepository {

    private val _orderedRewards = MutableStateFlow<List<OrderedReward>>(emptyList())
    private val orderedRewards = _orderedRewards.asStateFlow()

    init {
        fetchRewardList(
            onFetchSuccess = { rewardList ->
                _orderedRewards.value = rewardList.map { OrderedReward(it, 0) }
            },
            onFetchFailed = { _orderedRewards.value = emptyList() }
        )
    }

    override fun getAllRewards(): StateFlow<List<OrderedReward>> = orderedRewards

    override fun fetchRewardList(
        onFetchSuccess: (List<Reward>) -> Unit,
        onFetchFailed: (error: String) -> Unit
    ) {
        db.collection("reward").get()
            .addOnSuccessListener { result ->
                val rewardList = result.map { document -> createRewardFromDocument(document) }
                onFetchSuccess(rewardList)
            }
            .addOnFailureListener { error -> onFetchFailed(error.message.toString()) }
    }

    override fun getRewardById(rewardId: String): OrderedReward =
        _orderedRewards.value.first { it.reward.id == rewardId }

    override fun updateRewardCount(rewardId: String, count: Int) {
        _orderedRewards.value = _orderedRewards.value.map { orderedReward ->
            if (orderedReward.reward.id == rewardId)
                orderedReward.copy(count = count)
            else orderedReward
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun createRewardTransaction(
        orderedRewardList: List<OrderedReward>,
        totalPoints: Int,
        userId: String,
        onPostSuccess: (transactionId: String) -> Unit,
        onPostFailed: (error: String) -> Unit
    ) {
        val batch = db.batch()
        val userRef = db.collection("user").document(userId)
        val transactionRef = db.collection("rewardTransaction").document()

        val rewardList = getOrderedRewardArray(orderedRewardList)
        val newTransaction = hashMapOf(
            "id" to transactionRef.id,
            "userId" to userId,
            "rewardList" to rewardList,
            "totalPoints" to totalPoints.toLong(),
            "time" to TimeUtil.getTimestamp()
        )

        batch.set(transactionRef, newTransaction)
        batch.update(userRef, "points", FieldValue.increment(-totalPoints.toLong()))
        batch.update(userRef, "rewardTransactionList", FieldValue.arrayUnion(transactionRef.id))

        userRef.get()
            .addOnSuccessListener { document ->
                val existingRewardList = document.get("rewardList") as? List<Map<String, Any>> ?: emptyList()
                val updatedRewardList = mergeRewardLists(existingRewardList, rewardList)
                batch.update(userRef, "rewardList", updatedRewardList)
                batch.commit()
                    .addOnSuccessListener {
                        onPostSuccess(transactionRef.id)
                        _orderedRewards.value = _orderedRewards.value.map { OrderedReward(it.reward, 0) }
                    }
                    .addOnFailureListener { e -> onPostFailed(e.message.toString()) }
            }
            .addOnFailureListener { e -> onPostFailed(e.message.toString()) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun fetchRewardTransactionById(
        transactionId: String,
        onFetchSuccess: (RewardTransaction) -> Unit,
        onFetchFailed: (error: String) -> Unit
    ) {
        db.collection("rewardTransaction").document(transactionId).get()
            .addOnSuccessListener { document ->
                val rewardList = mutableListOf<OwnedReward>()
                val rewardListData = document.get("rewardList") as? List<Map<String, *>> ?: emptyList()
                rewardListData.forEach { data ->
                    val id = data["id"] as String
                    val count = data["count"] as Long
                    rewardList.add(
                        OwnedReward(
                            rewardId = id,
                            count = count.toInt()
                        )
                    )
                }
                val rewardTransaction = RewardTransaction(
                    id = document.getString("id") ?: "",
                    userId = document.getString("userId") ?: "",
                    totalPoints = document.getLong("totalPoints") ?: 0L,
                    time = document.getTimestamp("time") ?: Timestamp.now(),
                    rewardList = rewardList
                )
                onFetchSuccess(rewardTransaction)
            }
            .addOnFailureListener { error -> onFetchFailed(error.message.toString()) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun fetchRewardTransactionByUserId(
        userId: String,
        onFetchSuccess: (List<RewardTransaction>) -> Unit,
        onFetchFailed: (error: String) -> Unit
    ) {
        db.collection("user").document(userId).get()
            .addOnSuccessListener { document ->
                val rewardTransactionList = mutableListOf<RewardTransaction>()
                val rewardTransactionListId = document.get("rewardTransactionList") as List<String>
                if (rewardTransactionListId.isEmpty()) {
                    onFetchSuccess(emptyList())
                } else {
                    rewardTransactionListId.forEachIndexed { index, transactionId ->
                        fetchRewardTransactionById(
                            transactionId = transactionId,
                            onFetchSuccess = { rewardTransaction -> rewardTransactionList.add(rewardTransaction) },
                            onFetchFailed = onFetchFailed
                        )
                        if (index == rewardTransactionListId.size-1) { onFetchSuccess(rewardTransactionList) }
                    }
                }
            }
            .addOnFailureListener { error -> onFetchFailed(error.message.toString()) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun fetchOwnedRewardByUserId(
        userId: String,
        onFetchSuccess: (List<OrderedReward>) -> Unit,
        onFetchFailed: (error: String) -> Unit
    ) {
        db.collection("user").document(userId).get()
            .addOnSuccessListener { document ->
                val orderedRewardList = mutableListOf<OrderedReward>()
                val rewardList = document.get("rewardList") as List<Map<String, Any>>
                rewardList.forEachIndexed { index, rewardMap ->
                    fetchRewardById(
                        rewardId = rewardMap["id"] as String,
                        onFetchSuccess = { reward ->
                            orderedRewardList.add(OrderedReward(reward, (rewardMap["count"] as Long).toInt()))
                        },
                        onFetchFailed = onFetchFailed
                    )
                    if (index == rewardList.size-1) { onFetchSuccess(orderedRewardList) }
                }
            }
            .addOnFailureListener{ error -> onFetchFailed(error.message.toString()) }
    }

    override fun fetchRewardById(
        rewardId: String,
        onFetchSuccess: (Reward) -> Unit,
        onFetchFailed: (error: String) -> Unit
    ) {
        db.collection("reward").document(rewardId).get()
            .addOnSuccessListener { document ->
                val reward = createRewardFromDocument(document)
                onFetchSuccess(reward)
            }
            .addOnFailureListener { error -> onFetchFailed(error.message.toString()) }
    }

    private fun getOrderedRewardArray(orderedRewardList: List<OrderedReward>): List<Map<String, Any>> {
        return orderedRewardList.map { orderedReward ->
            mapOf(
                "count" to orderedReward.count,
                "id" to orderedReward.reward.id
            )
        }
    }

    private fun mergeRewardLists(existingRewardList: List<Map<String, Any>>, newRewardList: List<Map<String, Any>>): List<Map<String, Any>> {
        val rewardMap = existingRewardList.associateBy({ it["id"] as String }, { it.toMutableMap() }).toMutableMap()
        newRewardList.forEach { newReward ->
            val id = newReward["id"] as String
            val newCount = newReward["count"] as Int

            if (rewardMap.containsKey(id)) {
                val existingReward = rewardMap[id]
                val currentCount = existingReward?.get("count") as Long
                existingReward["count"] = currentCount + newCount
            } else {
                rewardMap[id] = newReward.toMutableMap()
            }
        }
        return rewardMap.values.toList()
    }


    @Suppress("UNCHECKED_CAST")
    private fun createRewardFromDocument(document: DocumentSnapshot): Reward {
        return Reward(
            id = document.id,
            name = document.getString("name") ?: "",
            imagePath = document.getString("imagePath") ?: "",
            value = document.getLong("value")?.toInt() ?: 0,
            description = document.getString("description") ?: "",
            termsAndConditions = document.get("termsAndConditions") as List<String>
        )
    }
}