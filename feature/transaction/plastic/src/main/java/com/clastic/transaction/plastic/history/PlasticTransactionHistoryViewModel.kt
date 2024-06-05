package com.clastic.transaction.plastic.history

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.PlasticTransactionRepository
import com.clastic.domain.repository.UserRepository
import com.clastic.model.transaction.plastic.PlasticTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class PlasticTransactionHistoryViewModel @Inject constructor(
    userRepository: UserRepository,
    private val plasticTransactionRepository: PlasticTransactionRepository
): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _plasticTransactionList = MutableStateFlow<List<PlasticTransaction>>(emptyList())
    val plasticTransactionList = _plasticTransactionList.asStateFlow()

    init {
        _isLoading.value = true
        userRepository.getUserInfo(
            onFetchSuccess = { user ->
                plasticTransactionRepository.getPlasticTransactionByUserId(
                    userId = user.email,
                    onFetchSuccess = { plasticTransactions ->
                        _plasticTransactionList.value = plasticTransactions.sortedByDescending { it.date }
                        _isLoading.value = false
                    },
                    onFetchFailed = {
                        _plasticTransactionList.value = emptyList()
                        _isLoading.value = false
                    }
                )
            },
            onFetchFailed = {
                _plasticTransactionList.value = emptyList()
                _isLoading.value = false
            }
        )
    }
}