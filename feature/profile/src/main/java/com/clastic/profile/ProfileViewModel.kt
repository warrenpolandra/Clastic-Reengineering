package com.clastic.profile

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    userRepository: UserRepository
): ViewModel() {

}