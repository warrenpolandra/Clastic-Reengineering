package com.clastic.transaction.mock

import android.net.Uri
import com.clastic.domain.repository.MissionRepository
import com.clastic.model.Mission
import com.clastic.model.MissionTransaction
import com.clastic.transaction.detail.MissionTransactionDetailState
import kotlinx.coroutines.flow.Flow

class MockMissionRepository(
    private val missionTransactionState: MissionTransactionDetailState,
    private val errorMessage: String,
    private val isSuccess: Boolean
): MissionRepository {
    override fun getMissionList(): Flow<List<Mission>> {
        TODO("Not yet implemented")
    }

    override fun fetchMissionById(
        missionId: String,
        onFetchSuccess: (Mission) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun postSubmitMission(
        userId: String,
        missionId: String,
        linkSubmission: String,
        totalPoints: Int,
        onPostSuccess: (transactionId: String) -> Unit,
        onPostFailed: (error: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun postSubmitMission(
        userId: String,
        missionId: String,
        imageUri: Uri,
        totalPoints: Int,
        onPostSuccess: (transactionId: String) -> Unit,
        onPostFailed: (error: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun fetchMissionTransactionById(
        transactionId: String,
        onFetchSuccess: (mission: Mission, missionTransaction: MissionTransaction) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        if (isSuccess) {
            onFetchSuccess(missionTransactionState.mission, missionTransactionState.missionTransaction)
        }
        else {
            onFetchFailed(errorMessage)
        }
    }

    override fun fetchMissionTransactionListByUserId(
        userId: String,
        onFetchSuccess: (List<MissionTransaction>) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}