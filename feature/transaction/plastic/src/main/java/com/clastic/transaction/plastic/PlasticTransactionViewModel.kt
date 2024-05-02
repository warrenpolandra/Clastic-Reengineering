package com.clastic.transaction.plastic

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.DropPointRepository
import com.clastic.domain.repository.PlasticKnowledgeRepository
import com.clastic.domain.repository.PlasticTransactionRepository
import com.clastic.domain.repository.UserRepository
import com.clastic.model.DropPoint
import com.clastic.model.User
import com.clastic.model.transaction.plastic.PlasticTransactionItem
import com.clastic.utils.TimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date
import javax.inject.Inject

@HiltViewModel
internal class PlasticTransactionViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dropPointRepository: DropPointRepository,
    private val plasticKnowledgeRepository: PlasticKnowledgeRepository,
    private val plasticTransactionRepository: PlasticTransactionRepository
): ViewModel() {
    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private val _plasticTypeList = MutableStateFlow<List<String>>(emptyList())
    val plasticTypeList = _plasticTypeList.asStateFlow()

    private val _currentDate = MutableStateFlow(Date())
    val currentDate = _currentDate.asStateFlow()

    private val _points = MutableStateFlow(0)
    val points = _points.asStateFlow()

    private val _dropPoint = MutableStateFlow(DropPoint())
    val dropPointName = _dropPoint.asStateFlow()

    private val _plasticTransactionItemList = MutableStateFlow(listOf(PlasticTransactionItem()))
    val plasticTransactionItemList = _plasticTransactionItemList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        _currentDate.value = TimeUtil.getCurrentDateTime()
        fetchPlasticTypeList()
        getDropPointName()
    }

    private fun fetchPlasticTypeList() {
        plasticKnowledgeRepository.fetchListPlasticKnowledge(
            onFetchSuccess = { listPlasticKnowledge ->
                _plasticTypeList.value = listPlasticKnowledge.map { it.tag }
            },
            onFetchFailed = { _plasticTypeList.value = emptyList() }
        )
    }

    private fun getDropPointName() {
        val ownerId = userRepository.getLoggedInUser()?.email ?: ""
        dropPointRepository.getDropPointByOwnerId(
            ownerId = ownerId,
            onFetchSuccess = { dropPoint ->
                _dropPoint.value = dropPoint
            },
            onFetchFailed = {
                _dropPoint.value = DropPoint()
            }
        )
    }

    private fun updatePoints() {
        _points.value = _plasticTransactionItemList.value.sumOf { it.points }
    }

    fun getUserById(userId: String) {
        userRepository.checkUserById(
            userId = userId,
            onFetchSuccess = { _, user -> _user.value = user ?: User()},
            onFetchFailed = { _user.value = User() }
        )
    }

    fun changeItemValueAtIndex(
        id: Int, weight: Float, points: Int
    ) {
        val currentItemList = _plasticTransactionItemList.value.toMutableList()
        currentItemList[id] = currentItemList[id].copy(
            currentItemList[id].plasticType, weight, points
        )
        _plasticTransactionItemList.value = currentItemList
        updatePoints()
    }

    fun changeItemTypeAtIndex(id: Int, plasticType: String) {
        val currentItemList = _plasticTransactionItemList.value.toMutableList()
        currentItemList[id] = currentItemList[id].copy(
            plasticType, currentItemList[id].weight, currentItemList[id].points
        )
        _plasticTransactionItemList.value = currentItemList
    }

    fun addNewTransactionItem() {
        val currentItemList = _plasticTransactionItemList.value.toMutableList()
        currentItemList.add(PlasticTransactionItem())
        _plasticTransactionItemList.value = currentItemList
        updatePoints()
    }

    fun removeLastTransactionItem() {
        val currentItemList = _plasticTransactionItemList.value.toMutableList()
        if (currentItemList.size > 0) {
            currentItemList.removeAt(_plasticTransactionItemList.value.size - 1)
            _plasticTransactionItemList.value = currentItemList
            updatePoints()
        }
    }

    fun submitTransaction(
        onPostSuccess: (transactionId: String) -> Unit,
        onPostFailed: (String) -> Unit
    ) {
        if (_plasticTransactionItemList.value.isNotEmpty()) {
            _isLoading.value = true
            plasticTransactionRepository.submitPlasticTransaction(
                date = _currentDate.value,
                dropPointId = _dropPoint.value.id,
                ownerId = userRepository.getLoggedInUser()?.email ?: "",
                totalPoints = _points.value.toLong(),
                userId = _user.value.email,
                plasticTransactionItemList = _plasticTransactionItemList.value,
                onPostSuccess = {
                    onPostSuccess(it)
                    _isLoading.value = false
                },
                onPostFailed = {
                    onPostFailed(it)
                    _isLoading.value = false
                }
            )
        }
    }
}