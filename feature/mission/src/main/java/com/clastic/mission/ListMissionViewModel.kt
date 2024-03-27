package com.clastic.mission

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.MissionRepository
import com.clastic.model.Mission
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ListMissionViewModel @Inject constructor(
    private val missionRepository: MissionRepository
): ViewModel() {
    private val _missionList = MutableStateFlow<List<Mission>>(emptyList())
    val missionList = _missionList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    init {
        fetchMissionList()
    }

    private fun fetchMissionList() {
        _errorMessage.value = ""
        _isLoading.value = true
        missionRepository.fetchMissions(
            onFetchSuccess = { missionList ->
                _missionList.value = missionList
                _isLoading.value = false
            },
            onFetchFailed = { error ->
                _isLoading.value = false
                _errorMessage.value = error
            }
        )
    }
}