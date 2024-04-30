package com.clastic.data.repository

import com.clastic.domain.repository.MissionRepository
import com.clastic.model.Impact
import com.clastic.model.Mission
import com.clastic.utils.TimeUtil
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
): MissionRepository {
    @Suppress("UNCHECKED_CAST")
    override fun fetchMissions(
        onFetchSuccess: (List<Mission>) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        val missionList = mutableListOf<Mission>()
        db.collection("mission").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val endDate = document.getTimestamp("endDate")?.seconds ?: 0

                    if (endDate > TimeUtil.getCurrentTimeSeconds()) {
                        missionList.add(
                            Mission(
                                id = document.id,
                                title = document.getString("title") ?: "",
                                description = document.getString("description") ?: "",
                                objectives = document.get("objectives") as List<String>,
                                imageUrl = document.getString("imageUrl") ?: "",
                                tags = document.get("tags") as List<String>,
                                reward = document.getLong("reward")?.toInt() ?: 0,
                                impacts = (document.get("impacts") as List<Map<String, Any>>).map { impact ->
                                    Impact(
                                        description = impact["description"] as String,
                                        imageUrl = impact["imageUrl"] as String,
                                        numberValue = (impact["numberValue"] as Long).toInt()
                                    )
                                },
                                endDate = endDate
                            )
                        )
                    }
                }
                onFetchSuccess(missionList)
            }
            .addOnFailureListener { e ->
                onFetchFailed(e.message.toString())
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun fetchMissionById(
        missionId: String,
        onFetchSuccess: (Mission) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        db.collection("mission").document(missionId).get()
            .addOnSuccessListener { document ->
                val mission = Mission(
                    id = document.id,
                    title = document.getString("title") ?: "",
                    description = document.getString("description") ?: "",
                    objectives = document.get("objectives") as List<String>,
                    imageUrl = document.getString("imageUrl") ?: "",
                    tags = document.get("tags") as List<String>,
                    reward = document.getLong("reward")?.toInt() ?: 0,
                    impacts = (document.get("impacts") as List<Map<String, Any>>).map { impact ->
                        Impact(
                            description = impact["description"] as String,
                            imageUrl = impact["imageUrl"] as String,
                            numberValue = (impact["numberValue"] as Long).toInt()
                        )
                    },
                    endDate = document.getTimestamp("endDate")?.seconds ?: 0
                )
                onFetchSuccess(mission)
            }
            .addOnFailureListener { e ->
                onFetchFailed(e.message.toString())
            }
    }
}