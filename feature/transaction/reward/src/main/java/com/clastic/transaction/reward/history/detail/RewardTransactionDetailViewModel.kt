package com.clastic.transaction.reward.history.detail

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.RewardRepository
import com.clastic.model.RewardTransaction
import com.clastic.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class RewardTransactionDetailViewModel @Inject constructor(
    private val rewardRepository: RewardRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<RewardTransaction>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchRewardTransactionById(transactionId: String) {
        rewardRepository.fetchRewardTransactionById(
            transactionId = transactionId,
            onFetchSuccess = { rewardTransaction -> _uiState.value = UiState.Success(rewardTransaction) },
            onFetchFailed = { error -> _uiState.value = UiState.Error(error) }
        )
    }
}