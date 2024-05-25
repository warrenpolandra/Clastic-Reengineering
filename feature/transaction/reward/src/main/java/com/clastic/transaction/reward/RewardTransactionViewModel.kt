package com.clastic.transaction.reward

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clastic.domain.repository.RewardRepository
import com.clastic.domain.repository.UserRepository
import com.clastic.model.OrderedReward
import com.clastic.model.User
import com.clastic.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class RewardTransactionViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val rewardRepository: RewardRepository
): ViewModel(){
    private val _uiState = MutableStateFlow<UiState<List<OrderedReward>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    init {
        getOrderedRewards()
        getUserInfo()
    }

    private fun getOrderedRewards() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            rewardRepository.getAllRewards()
                .catch { _uiState.value = UiState.Error(it.message.toString()) }
                .collect { orderedRewardList ->
                    _uiState.value = UiState.Success(orderedRewardList.filter { it.count != 0 })
                }
        }
    }

    private fun getUserInfo() {
        userRepository.getUserInfo(
            onFetchSuccess = { user ->
                _user.value = user
            },
            onFetchFailed = { _user.value = User() }
        )
    }

    fun updateRewardCount(
        rewardId: String,
        count: Int,
        onAddError: () -> Unit
    ) {
        if (count > 99) onAddError()
        else if (count >= 0) {
            rewardRepository.updateRewardCount(
                rewardId = rewardId,
                count = count
            )
        }
    }

    fun createRewardTransaction(
        orderedRewardList: List<OrderedReward>,
        totalPoints: Int,
        userId: String,
        onPostSuccess: (transactionId: String) -> Unit,
        onPostFailed: (error: String) -> Unit
    ) {
        _uiState.value = UiState.Loading
        rewardRepository.createRewardTransaction(
            orderedRewardList = orderedRewardList,
            totalPoints = totalPoints,
            userId = userId,
            onPostSuccess = onPostSuccess,
            onPostFailed = onPostFailed
        )
    }
}