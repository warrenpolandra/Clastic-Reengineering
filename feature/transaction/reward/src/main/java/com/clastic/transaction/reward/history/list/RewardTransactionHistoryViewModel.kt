package com.clastic.transaction.reward.history.list

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.RewardRepository
import com.clastic.domain.repository.UserRepository
import com.clastic.model.RewardTransaction
import com.clastic.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class RewardTransactionHistoryViewModel @Inject constructor(
    userRepository: UserRepository,
    private val rewardRepository: RewardRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<RewardTransaction>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.value = UiState.Loading
        userRepository.getUserInfo(
            onFetchSuccess = { user -> fetchRewardTransactions(user.email) },
            onFetchFailed = { error -> _uiState.value = UiState.Error(error) }
        )
    }

    private fun fetchRewardTransactions(userId: String) {
        rewardRepository.fetchRewardTransactionByUserId(
            userId = userId,
            onFetchSuccess = { rewardTransactionList ->
                _uiState.value = UiState.Success(rewardTransactionList.sortedByDescending { it.time })
            },
            onFetchFailed = { error -> _uiState.value = UiState.Error(error) }
        )
    }
}