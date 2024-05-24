package com.clastic.reward.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clastic.domain.repository.RewardRepository
import com.clastic.model.OrderedReward
import com.clastic.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class RewardDetailViewModel @Inject constructor(
    private val rewardRepository: RewardRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<OrderedReward>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getRewardById(rewardId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(rewardRepository.getRewardById(rewardId))
        }
    }

    fun addToCart(
        rewardId: String,
        count: Int
    ) {
        rewardRepository.updateRewardCount(
            rewardId = rewardId,
            count = count
        )
    }
}