package com.clastic.splashscreen

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class SplashScreenViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    fun isUserLoggedIn(): Boolean {
        return userRepository.getLoggedInUser() != null
    }
}