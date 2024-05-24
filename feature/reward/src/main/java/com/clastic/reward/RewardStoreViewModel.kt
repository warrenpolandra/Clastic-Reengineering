package com.clastic.reward

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clastic.domain.repository.RewardRepository
import com.clastic.model.Reward
import com.clastic.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class RewardStoreViewModel @Inject constructor(
    private val rewardRepository: RewardRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Reward>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init { fetchRewardList() }

    private fun fetchRewardList() {
        viewModelScope.launch {
            rewardRepository.getAllRewards()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { rewardList ->
                    _uiState.value = UiState.Success(rewardList.map { it.reward })
                }
        }
    }
}