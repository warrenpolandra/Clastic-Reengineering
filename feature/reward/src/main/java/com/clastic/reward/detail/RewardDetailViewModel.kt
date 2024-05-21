package com.clastic.reward.detail

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.RewardRepository
import com.clastic.model.Reward
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class RewardDetailViewModel @Inject constructor(
    private val rewardRepository: RewardRepository
): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _reward = MutableStateFlow(Reward())
    val reward = _reward.asStateFlow()

    private val _itemCount = MutableStateFlow(0)
    val itemCount = _itemCount.asStateFlow()

    fun fetchRewardById(
        rewardId: String,
        onFetchFailed: (String) -> Unit
    ) {
        rewardRepository.fetchRewardById(
            rewardId = rewardId,
            onFetchSuccess = { reward -> _reward.value = reward },
            onFetchFailed = onFetchFailed
        )
    }

    fun reduceItemCount(
        onReduceFailed: (String) -> Unit
    ) {
        if (_itemCount.value > 0)
            _itemCount.value--
        else
            onReduceFailed("Minimum item is 0")
    }

    fun increaseItemCount(
        onIncreaseFailed: (String) -> Unit
    ) {
        if (_itemCount.value < 100)
            _itemCount.value++
        else
            onIncreaseFailed("Maximum item is 99")
    }

    fun addToCart(
        onAddFailed: (String) -> Unit
    ) {
        _isLoading.value = true
        _itemCount.value = 0
        // TODO
    }
}