package com.clastic.reward.inventory

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.RewardRepository
import com.clastic.domain.repository.UserRepository
import com.clastic.model.OrderedReward
import com.clastic.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class RewardInventoryViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val rewardRepository: RewardRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<OrderedReward>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchOwnedReward() {
        userRepository.getUserInfo(
            onFetchSuccess = { user ->
                rewardRepository.fetchOwnedRewardByUserId(
                    userId = user.email,
                    onFetchSuccess = { orderedRewardList ->
                        _uiState.value = UiState.Success(orderedRewardList)
                    },
                    onFetchFailed = { error -> _uiState.value = UiState.Error(error) }
                )
            },
            onFetchFailed = { error -> _uiState.value = UiState.Error(error) }
        )
    }
}