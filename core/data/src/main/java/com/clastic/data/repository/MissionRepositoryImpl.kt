package com.clastic.data.repository

import android.content.Context
import android.net.Uri
import com.clastic.domain.repository.MissionRepository
import com.clastic.model.Impact
import com.clastic.model.Mission
import com.clastic.model.MissionTransaction
import com.clastic.utils.TimeUtil
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(
    private val appContext: Context,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
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

    override fun postSubmitMission(
        userId: String,
        missionId: String,
        imageUri: Uri,
        totalPoints: Int,
        onPostSuccess: (transactionId: String) -> Unit,
        onPostFailed: (error: String) -> Unit
    ) {
        val byteArray = appContext.contentResolver
            .openInputStream(imageUri)
            ?.use { it.readBytes() }

        byteArray?.let {
            val storageRef = storage.reference
            val uniqueImageName = UUID.randomUUID()
            val spaceRef = storageRef.child("/Mission Submission/$missionId/$uniqueImageName.jpg")
            spaceRef.putBytes(byteArray)
                .addOnSuccessListener {
                    spaceRef.downloadUrl.addOnSuccessListener { uri ->
                        createMissionSubmissionDocument(
                            userId = userId,
                            missionId = missionId,
                            totalPoints = totalPoints,
                            linkSubmission = uri.toString(),
                            onPostSuccess = onPostSuccess,
                            onPostFailed = onPostFailed
                        )
                    }
                }
                .addOnFailureListener { error -> onPostFailed(error.message.toString()) }
        }
    }

    override fun postSubmitMission(
        userId: String,
        missionId: String,
        linkSubmission: String,
        totalPoints: Int,
        onPostSuccess: (transactionId: String) -> Unit,
        onPostFailed: (error: String) -> Unit
    ) {
        createMissionSubmissionDocument(
            userId = userId,
            missionId = missionId,
            totalPoints = totalPoints,
            linkSubmission = linkSubmission,
            onPostSuccess = onPostSuccess,
            onPostFailed = onPostFailed
        )
    }

    override fun fetchMissionTransactionById(
        transactionId: String,
        onFetchSuccess: (mission: Mission, missionTransaction: MissionTransaction) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        db.collection("missionSubmission").document(transactionId).get()
            .addOnSuccessListener { missionSubmissionDocument ->
                db.collection("mission")
                    .document(missionSubmissionDocument.getString("missionId") ?: "").get()
                    .addOnSuccessListener { missionDocument ->
                        val mission = getMissionFromDocument(missionDocument)
                        val missionTransaction = getMissionTransactionFromDocument(missionSubmissionDocument)
                        onFetchSuccess(mission, missionTransaction)
                    }
                    .addOnFailureListener { error -> onFetchFailed(error.message.toString()) }
            }
            .addOnFailureListener { error -> onFetchFailed(error.message.toString()) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun fetchMissionTransactionListByUserId(
        userId: String,
        onFetchSuccess: (List<MissionTransaction>) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        db.collection("user").document(userId).get()
            .addOnSuccessListener { document ->
                val missionTransactionList = mutableListOf<MissionTransaction>()
                val missionTransactionListId = document.get("missionSubmissionList") as List<String>
                if (missionTransactionListId.isEmpty()) { onFetchSuccess(emptyList()) } else {
                    missionTransactionListId.forEachIndexed { index, missionTransactionId ->
                        fetchMissionTransactionById(
                            transactionId = missionTransactionId,
                            onFetchSuccess = { _, missionTransaction ->
                                missionTransactionList.add(missionTransaction)
                                if (index == missionTransactionListId.size-1) { onFetchSuccess(missionTransactionList) }
                            },
                            onFetchFailed = onFetchFailed
                        )
                    }
                }
            }
            .addOnFailureListener { error -> onFetchFailed(error.message.toString()) }
    }

    private fun getMissionTransactionFromDocument(document: DocumentSnapshot): MissionTransaction {
        return MissionTransaction(
            id = document.id,
            missionId = document.getString("missionId") ?: "",
            submissionUrl = document.getString("submissionUrl") ?: "",
            time = document.getTimestamp("time") ?: Timestamp.now(),
            totalPoints = document.getLong("totalPoints")?.toInt() ?: 0,
            userId = document.getString("userId") ?: ""
        )
    }

    @Suppress("UNCHECKED_CAST")
    private fun getMissionFromDocument(document: DocumentSnapshot): Mission {
        return Mission(
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
    }

    private fun createMissionSubmissionDocument(
        userId: String,
        missionId: String,
        totalPoints: Int,
        linkSubmission: String,
        onPostSuccess: (transactionId: String) -> Unit,
        onPostFailed: (error: String) -> Unit
    ) {
        val batch = db.batch()
        val userRef = db.collection("user").document(userId)
        val transactionRef = db.collection("missionSubmission").document()

        val newTransaction = hashMapOf(
            "id" to transactionRef.id,
            "missionId" to missionId,
            "submissionUrl" to linkSubmission,
            "time" to TimeUtil.getTimestamp(),
            "totalPoints" to totalPoints,
            "userId" to userId
        )

        batch.set(transactionRef, newTransaction)
        batch.update(userRef, "points", FieldValue.increment(totalPoints.toLong()))
        batch.update(userRef, "missionSubmissionList", FieldValue.arrayUnion(transactionRef.id))

        batch.commit()
            .addOnSuccessListener { onPostSuccess(transactionRef.id) }
            .addOnFailureListener { error -> onPostFailed(error.message.toString()) }
    }
}