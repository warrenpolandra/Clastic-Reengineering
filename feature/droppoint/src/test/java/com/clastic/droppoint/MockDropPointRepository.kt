package com.clastic.droppoint

import com.clastic.domain.repository.DropPointRepository
import com.clastic.model.DropPoint
import com.google.android.gms.maps.model.LatLng

class MockDropPointRepository: DropPointRepository {
    override fun getDropPointList(
        onFetchSuccess: (List<DropPoint>, LatLng) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {}

    override fun getDropPointByOwnerId(
        ownerId: String,
        onFetchSuccess: (DropPoint) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {}

    override fun getDropPointById(
        dropPointId: String,
        onFetchSuccess: (DropPoint) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {}
}