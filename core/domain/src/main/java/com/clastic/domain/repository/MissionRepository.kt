package com.clastic.domain.repository

import com.clastic.model.Mission

interface MissionRepository {
    fun fetchMissions(
        onFetchSuccess: (List<Mission>) -> Unit,
        onFetchFailed: (String) -> Unit
    )
}