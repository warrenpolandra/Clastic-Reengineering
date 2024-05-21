package com.clastic.data.repository

import com.clastic.domain.repository.RewardRepository
import com.clastic.model.Reward
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class RewardRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
): RewardRepository {
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

    @Suppress("UNCHECKED_CAST")
    private fun createRewardFromDocument(document: DocumentSnapshot): Reward {
        return Reward(
            id = document.id,
            name = document.getString("name") ?: "",
            imagePath = document.getString("imagePath") ?: "",
            value = document.getLong("value")?.toInt() ?: 0,
            termsAndConditions = document.get("termsAndConditions") as List<String>
        )
    }
}