package com.clastic.profile

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.UserRepository
import com.clastic.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    init {
        fetchUserInfo()
    }

    private fun fetchUserInfo() {
        _isLoading.value = true
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

    fun userLogout(
        onSignOutSuccess: () -> Unit,
        onSignOutFailed: (error: String) -> Unit
    ) {
        userRepository.userSignOut(
            onSignOutSuccess = onSignOutSuccess,
            onSignOutFailed = onSignOutFailed
        )
    }
}