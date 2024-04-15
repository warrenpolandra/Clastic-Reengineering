package com.clastic.droppoint

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.DropPointRepository
import com.clastic.model.DropPoint
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DropPointMapViewModel @Inject constructor(
    private val dropPointRepository: DropPointRepository
): ViewModel() {
    private val _dropPointList = MutableStateFlow<List<DropPoint>>(emptyList())
    val dropPointList = _dropPointList.asStateFlow()

    private val _boundsCenter = MutableStateFlow(LatLng(6.175, 106.8283))
    val boundsCenter = _boundsCenter.asStateFlow()

    init {
        fetchDropPointList()
    }

    private fun fetchDropPointList() {
        dropPointRepository.getDropPointList(
            onFetchSuccess = { dropPointList, boundsCenter ->
                _dropPointList.value = dropPointList
                _boundsCenter.value = boundsCenter
            },
            onFetchFailed = { /*TODO*/ }
        )
    }
}