package com.clastic.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clastic.domain.repository.MissionRepository
import com.clastic.domain.repository.PlasticKnowledgeRepository
import com.clastic.domain.repository.UserRepository
import com.clastic.model.Mission
import com.clastic.model.PlasticKnowledge
import com.clastic.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val plasticKnowledgeRepository: PlasticKnowledgeRepository,
    private val missionRepository: MissionRepository
): ViewModel() {
    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private val _listPlasticKnowledge = MutableStateFlow<List<PlasticKnowledge>>(emptyList())
    val listPlasticKnowledge = _listPlasticKnowledge.asStateFlow()

    private val _missionList = MutableStateFlow<List<Mission>>(emptyList())
    val missionList = _missionList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        fetchUserInfo()
        fetchListPlasticKnowledge()
        fetchMissionList()
    }

    private fun fetchMissionList() {
        viewModelScope.launch {
            missionRepository.getMissionList()
                .collect { missionList ->
                    _isLoading.value = false
                    _missionList.value = missionList
                }
        }
    }

    private fun fetchListPlasticKnowledge() {
        _isLoading.value = true
        plasticKnowledgeRepository.fetchListPlasticKnowledge(
            onFetchSuccess = { listPlasticKnowledge ->
                _listPlasticKnowledge.value = listPlasticKnowledge
            },
            onFetchFailed = {
                _listPlasticKnowledge.value = emptyList()
            }
        )
    }

    private fun fetchUserInfo() {
        userRepository.getUserInfo(
            onFetchSuccess = { user ->
                _user.value = user
                _isLoading.value = false
            },
            onFetchFailed = {
                _isLoading.value = false
            }
        )
    }
}