package com.clastic.reward

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.RewardRepository
import com.clastic.model.Reward
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class RewardStoreViewModel @Inject constructor(
    private val rewardRepository: RewardRepository
): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _rewardList = MutableStateFlow<List<Reward>>(emptyList())
    val rewardList = _rewardList.asStateFlow()

    init {
        fetchRewardList()
    }

    private fun fetchRewardList() {
        rewardRepository.fetchRewardList(
            onFetchSuccess = { rewardList ->
                _rewardList.value = rewardList
            },
            onFetchFailed = { _rewardList.value = emptyList() }
        )
    }
}