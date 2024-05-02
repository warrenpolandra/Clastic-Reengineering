package com.clastic.transaction.plastic.component

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.DropPointRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class PlasticTransactionHistoryCardViewModel @Inject constructor(
    private val dropPointRepository: DropPointRepository
): ViewModel() {
    private val _dropPointName = MutableStateFlow("")
    val dropPointName = _dropPointName.asStateFlow()

    fun fetchDropPointNameById(
        dropPointId: String
    ) {
        dropPointRepository.getDropPointById(
            dropPointId,
            onFetchSuccess = { dropPoint ->
                _dropPointName.value = dropPoint.name
            },
            onFetchFailed = {
                _dropPointName.value = ""
            }
        )
    }
}