package com.clastic.transaction.history

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.MissionRepository
import com.clastic.domain.repository.UserRepository
import com.clastic.model.MissionTransaction
import com.clastic.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class MissionTransactionHistoryViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val missionRepository: MissionRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<MissionTransaction>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchMissionTransactionHistory() {
        userRepository.getUserInfo(
            onFetchSuccess = { user ->
                missionRepository.fetchMissionTransactionListByUserId(
                    userId = user.email,
                    onFetchSuccess = { missionTransactionList ->
                        _uiState.value = UiState.Success(missionTransactionList)
                    },
                    onFetchFailed = { error -> _uiState.value = UiState.Error(error) }
                )
            },
            onFetchFailed = { error -> _uiState.value = UiState.Error(error) }
        )
    }
}