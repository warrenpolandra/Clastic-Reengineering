package com.clastic.transaction.reward.component.item

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.RewardRepository
import com.clastic.model.Reward
import com.clastic.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class RewardTransactionResultItemViewmodel @Inject constructor(
    private val rewardRepository: RewardRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Reward>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchRewardById(rewardId: String) {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(rewardRepository.getRewardById(rewardId).reward)
    }
}