package com.clastic.leaderboard

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.UserRepository
import com.clastic.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class LeaderboardViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList = _userList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _currentUser = MutableStateFlow(User())
    val currentUser = _currentUser.asStateFlow()

    private val _currentUserPosition = MutableStateFlow(1)
    val currentUserPosition = _currentUserPosition.asStateFlow()

    init {
        fetchCurrentUser()
        fetchSortedUserList()
    }
    private fun fetchCurrentUser() {
        _isLoading.value = true
        userRepository.getUserInfo(
            onFetchSuccess = {
                user -> _currentUser.value = user
                _isLoading.value = false
            },
            onFetchFailed = {
                _isLoading.value = false
                _currentUser.value = User()
            }
        )
    }

    private fun fetchSortedUserList() {
        _isLoading.value = true
        userRepository.fetchAllUsers(
            onFetchSuccess = { userList ->
                _isLoading.value = false
                _userList.value = userList.sortedByDescending { it.totalPlastic }
                _userList.value.forEachIndexed { index, user ->
                    if (user.userId == _currentUser.value.userId) {
                        _currentUserPosition.value = index+1
                    }
                }
            },
            onFetchFailed = {
                _userList.value = emptyList()
                _isLoading.value = false
            }
        )
    }
}