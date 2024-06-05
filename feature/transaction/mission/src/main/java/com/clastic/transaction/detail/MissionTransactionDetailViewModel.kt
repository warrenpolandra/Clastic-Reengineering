package com.clastic.transaction.detail

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.MissionRepository
import com.clastic.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class MissionTransactionDetailViewModel @Inject constructor(
    private val missionRepository: MissionRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<MissionTransactionDetailState>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchMissionTransactionById(transactionId: String) {
        missionRepository.fetchMissionTransactionById(
            transactionId = transactionId,
            onFetchSuccess = { mission, missionTransaction ->
                _uiState.value = UiState.Success(MissionTransactionDetailState(mission, missionTransaction))
            },
            onFetchFailed = { error -> _uiState.value = UiState.Error(error) }
        )
    }
}