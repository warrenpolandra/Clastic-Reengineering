package com.clastic.domain.repository

import com.clastic.model.DropPoint
import com.google.android.gms.maps.model.LatLng

interface DropPointRepository {
    fun getDropPointList(
        onFetchSuccess: (List<DropPoint>, LatLng) -> Unit,
        onFetchFailed: (String) -> Unit
    )

    fun getDropPointByOwnerId(
        ownerId: String,
        onFetchSuccess: (DropPoint) -> Unit,
        onFetchFailed: (String) -> Unit
    )

    fun getDropPointById(
        dropPointId: String,
        onFetchSuccess: (DropPoint) -> Unit,
        onFetchFailed: (String) -> Unit
    )
}