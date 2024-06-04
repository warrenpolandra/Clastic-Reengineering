package com.clastic.domain.repository

import android.net.Uri
import com.clastic.model.Mission
import com.clastic.model.MissionTransaction
import kotlinx.coroutines.flow.Flow

interface MissionRepository {
    fun getMissionList(): Flow<List<Mission>>

    fun fetchMissionById(
        missionId: String,
        onFetchSuccess: (Mission) -> Unit,
        onFetchFailed: (String) -> Unit
    )

    fun postSubmitMission(
        userId: String,
        missionId: String,
        linkSubmission: String,
        totalPoints: Int,
        onPostSuccess: (transactionId: String) -> Unit,
        onPostFailed: (error: String) -> Unit
    )

    fun postSubmitMission(
        userId: String,
        missionId: String,
        imageUri: Uri,
        totalPoints: Int,
        onPostSuccess: (transactionId: String) -> Unit,
        onPostFailed: (error: String) -> Unit
    )

    fun fetchMissionTransactionById(
        transactionId: String,
        onFetchSuccess: (mission: Mission, missionTransaction: MissionTransaction) -> Unit,
        onFetchFailed: (String) -> Unit
    )

    fun fetchMissionTransactionListByUserId(
        userId: String,
        onFetchFailed: (String) -> Unit
    ): Flow<List<MissionTransaction>>
}