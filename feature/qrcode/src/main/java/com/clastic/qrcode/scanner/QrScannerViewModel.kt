package com.clastic.qrcode.scanner

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class QrScannerViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _isUserExist = MutableStateFlow<Boolean?>(null)
    val isUserExist = _isUserExist.asStateFlow()

    fun checkUserById(
        userId: String,
        onUserFound: () -> Unit
    ) {
        userRepository.checkUserById(
            userId,
            onFetchSuccess = { isUserExist, _ ->
                _isUserExist.value = isUserExist
                if (isUserExist) { onUserFound() }
                _isUserExist.value = null
            },
            onFetchFailed = { /*TODO*/ }
        )
    }
}