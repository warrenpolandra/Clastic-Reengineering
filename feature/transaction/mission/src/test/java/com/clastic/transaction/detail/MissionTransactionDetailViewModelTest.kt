package com.clastic.transaction.detail

import com.clastic.model.Mission
import com.clastic.model.MissionTransaction
import com.clastic.transaction.mock.MockMissionRepository
import com.clastic.ui.UiState
import com.google.firebase.Timestamp
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class MissionTransactionDetailViewModelTest {
    private lateinit var viewModel: MissionTransactionDetailViewModel
    private lateinit var mockMissionRepository: MockMissionRepository
    private val transactionId = "SAMPLE_TRANSACTION_ID"
    private val mission = Mission(
        id = "SAMPLE_MISSION_ID",
        title = "SAMPLE_TITLE",
        description = "SAMPLE_DESCRIPTION",
        imageUrl = "SAMPLE_IMAGE_URL",
        objectives = listOf("OBJ1", "OBJ2"),
        tags = listOf("TAG1", "TAG2"),
        reward = 100,
        impacts = emptyList(),
        endDate = 0L
    )
    private val missionTransaction = MissionTransaction(
        id = transactionId,
        isPicture = true,
        submissionUrl = "SAMPLE_URL",
        userId = "SAMPLE_ID",
        time = Timestamp.now(),
        missionId = "SAMPLE_MISSION_ID",
        totalPoints = 1000
    )

    private val missionTransactionDetailState = MissionTransactionDetailState(
        mission,
        missionTransaction
    )

    @Before
    fun setup() {
        mockMissionRepository = MockMissionRepository(missionTransactionDetailState, "", true)
        viewModel = MissionTransactionDetailViewModel(mockMissionRepository)
    }

    @Test
    fun `should fetch mission transaction by id`() {
        viewModel.fetchMissionTransactionById(transactionId)
        assert(viewModel.uiState.value is UiState.Success)
        val state = viewModel.uiState.value as UiState.Success
        assertEquals(missionTransactionDetailState, state.data)
    }

    @Test
    fun `should return error when fetching mission transaction by id`() {
        mockMissionRepository = MockMissionRepository(missionTransactionDetailState, "Error", false)
        viewModel = MissionTransactionDetailViewModel(mockMissionRepository)
        viewModel.fetchMissionTransactionById(transactionId)
        assert(viewModel.uiState.value is UiState.Error)
    }
}