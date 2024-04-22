package com.clastic.transaction.plastic

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.DropPointRepository
import com.clastic.domain.repository.PlasticKnowledgeRepository
import com.clastic.domain.repository.UserRepository
import com.clastic.model.transaction.PlasticTransactionItem
import com.clastic.utils.TimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PlasticTransactionViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dropPointRepository: DropPointRepository,
    private val plasticKnowledgeRepository: PlasticKnowledgeRepository,
): ViewModel() {
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _plasticTypeList = MutableStateFlow<List<String>>(emptyList())
    val plasticTypeList = _plasticTypeList.asStateFlow()

    private val _currentDate = MutableStateFlow("")
    val currentDate = _currentDate.asStateFlow()

    private val _points = MutableStateFlow(0)
    val points = _points.asStateFlow()

    private val _dropPointName = MutableStateFlow("")
    val dropPointName = _dropPointName.asStateFlow()

    private val _plasticTransactionItemList = MutableStateFlow(listOf(PlasticTransactionItem()))
    val plasticTransactionItemList = _plasticTransactionItemList.asStateFlow()

    init {
        _currentDate.value = TimeUtil.getCurrentDateTimeString()
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
                _dropPointName.value = dropPoint.name
            },
            onFetchFailed = {
                _dropPointName.value = ""
            }
        )
    }

    private fun updatePoints() {
        _points.value = _plasticTransactionItemList.value.sumOf { it.points }
    }

    fun getUserById(userId: String) {
        userRepository.checkUserById(
            userId = userId,
            onFetchSuccess = { _, user -> _username.value = user?.username ?: "" },
            onFetchFailed = { _username.value = "" }
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
}