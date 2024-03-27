package com.clastic.data.repository

import com.clastic.domain.repository.MissionRepository
import com.clastic.model.Impact
import com.clastic.model.Mission
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(): MissionRepository {
    private val db = FirebaseFirestore.getInstance()

    @Suppress("UNCHECKED_CAST")
    override fun fetchMissions(
        onFetchSuccess: (List<Mission>) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        val missionList = mutableListOf<Mission>()
        db.collection("mission").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val endDate = document.getTimestamp("endDate")

                    if (endDate != null) {
                        if (endDate.seconds > Timestamp.now().seconds) {
                            missionList.add(
                                Mission(
                                    id = document.id,
                                    title = document.getString("title") ?: "",
                                    description = document.getString("description") ?: "",
                                    objectives = document.get("objectives") as List<String>,
                                    imageUrl = document.getString("imageUrl") ?: "",
                                    tags = document.get("tags") as List<String>,
                                    reward = document.getLong("reward")?.toInt() ?: 0,
                                    impacts = document.get("impacts") as List<Map<String, String>> as List<Impact>,
                                    endDate = endDate
                                )
                            )
                        }
                    }
                }
                onFetchSuccess(missionList)
            }
            .addOnFailureListener { e ->
                onFetchFailed(e.message.toString())
            }
    }
}