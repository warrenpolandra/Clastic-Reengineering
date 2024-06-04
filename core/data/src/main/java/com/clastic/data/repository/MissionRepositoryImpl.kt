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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(
    private val appContext: Context,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
): MissionRepository {

    private val _missionTransactionList = MutableStateFlow<List<MissionTransaction>>(emptyList())
    private val missionTransactionList = _missionTransactionList.asStateFlow()

    private val _missionList = MutableStateFlow<List<Mission>>(emptyList())
    private val missionList = _missionList.asStateFlow()

    init {
        fetchMissions(
            onFetchSuccess = { missionList -> _missionList.value = missionList },
            onFetchFailed = { _missionList.value = emptyList() }
        )
    }

    @Suppress("UNCHECKED_CAST")
    private fun fetchMissions(
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

    override fun getMissionList(): Flow<List<Mission>> = missionList

    override fun fetchMissionById(
        missionId: String,
        onFetchSuccess: (Mission) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        val findMission = _missionList.value.firstOrNull { mission -> mission.id == missionId }
        if (findMission != null) onFetchSuccess(findMission)
        else onFetchFailed("No such mission")
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
                            isPicture = true,
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
            isPicture = false,
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
        onFetchFailed: (String) -> Unit
    ): Flow<List<MissionTransaction>> {
        if (_missionTransactionList.value.isEmpty()) {
            db.collection("user").document(userId).get()
                .addOnSuccessListener { document ->
                    val findMissionTransactionList = mutableListOf<MissionTransaction>()
                    val missionTransactionListId = document.get("missionSubmissionList") as List<String>
                    if (missionTransactionListId.isEmpty()) { _missionTransactionList.value = emptyList() } else {
                        missionTransactionListId.forEachIndexed { index, missionTransactionId ->
                            fetchMissionTransactionById(
                                transactionId = missionTransactionId,
                                onFetchSuccess = { _, missionTransaction ->
                                    findMissionTransactionList.add(missionTransaction)
                                    if (index == missionTransactionListId.size-1) {
                                        _missionTransactionList.value = findMissionTransactionList
                                    }
                                },
                                onFetchFailed = onFetchFailed
                            )
                        }
                    }
                }
                .addOnFailureListener { error -> onFetchFailed(error.message.toString()) }
        }
        return missionTransactionList
    }

    private fun getMissionTransactionFromDocument(document: DocumentSnapshot): MissionTransaction {
        return MissionTransaction(
            id = document.id,
            missionId = document.getString("missionId") ?: "",
            submissionUrl = document.getString("submissionUrl") ?: "",
            time = document.getTimestamp("time") ?: Timestamp.now(),
            totalPoints = document.getLong("totalPoints")?.toInt() ?: 0,
            userId = document.getString("userId") ?: "",
            isPicture = document.getBoolean("isPicture") ?: false
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
        isPicture: Boolean,
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
            "isPicture" to isPicture,
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
            .addOnSuccessListener {
                _missionTransactionList.value = emptyList()
                onPostSuccess(transactionRef.id)
            }
            .addOnFailureListener { error -> onPostFailed(error.message.toString()) }
    }
}