package com.clastic.transaction.plastic.detail

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.DropPointRepository
import com.clastic.domain.repository.PlasticTransactionRepository
import com.clastic.domain.repository.UserRepository
import com.clastic.model.DropPoint
import com.clastic.model.User
import com.clastic.model.transaction.plastic.PlasticTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class PlasticTransactionDetailViewModel @Inject constructor(
    private val dropPointRepository: DropPointRepository,
    private val plasticTransactionRepository: PlasticTransactionRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val _plasticTransaction = MutableStateFlow(PlasticTransaction())
    val plasticTransaction = _plasticTransaction.asStateFlow()

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private val _dropPoint = MutableStateFlow(DropPoint())
    val dropPoint = _dropPoint.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun fetchPlasticTransaction(
        plasticTransactionId: String,
        onFetchFailed: (error: String) -> Unit
    ) {
        _isLoading.value = true
        plasticTransactionRepository.getPlasticTransactionById(
            plasticTransactionId = plasticTransactionId,
            onFetchSuccess = { plasticTransaction ->
                _plasticTransaction.value = plasticTransaction
                fetchDropPointById(
                    dropPointId = _plasticTransaction.value.dropPointId,
                    onFetchFailed = onFetchFailed
                )
                fetchUserById(
                    userId = _plasticTransaction.value.userId,
                    onFetchFailed = onFetchFailed
                )
            },
            onFetchFailed = {
                _isLoading.value = false
                onFetchFailed(it)
            }
        )
    }

    private fun fetchDropPointById(
        dropPointId: String,
        onFetchFailed: (error: String) -> Unit
    ) {
        _isLoading.value = true
        dropPointRepository.getDropPointById(
            dropPointId = dropPointId,
            onFetchSuccess = { dropPoint ->
                _dropPoint.value = dropPoint
                _isLoading.value = false
            },
            onFetchFailed = {
                _isLoading.value = false
                onFetchFailed(it)
            }
        )
    }

    private fun fetchUserById(
        userId: String,
        onFetchFailed: (error: String) -> Unit
    ) {
        _isLoading.value = true
        userRepository.checkUserById(
            userId = userId,
            onFetchSuccess = { _, user ->
                _user.value = user ?: User()
                _isLoading.value = false
            },
            onFetchFailed = {
                _isLoading.value = false
                onFetchFailed(it)
            }
        )
    }
}