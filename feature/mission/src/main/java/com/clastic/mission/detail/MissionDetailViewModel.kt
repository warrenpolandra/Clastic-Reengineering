package com.clastic.mission.detail

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.MissionRepository
import com.clastic.model.Mission
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MissionDetailViewModel @Inject constructor(
    private val missionRepository: MissionRepository
): ViewModel() {
    private val _mission = MutableStateFlow(Mission())
    val mission = _mission.asStateFlow()

    fun fetchMissionById(
        missionId: String,
        onFetchFailed: (String) -> Unit
    ) {
        missionRepository.fetchMissionById(
            missionId = missionId,
            onFetchSuccess = { mission ->
                _mission.value = mission
            },
            onFetchFailed = onFetchFailed
        )
    }
}