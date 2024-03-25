package com.clastic.authentication.login

import android.content.Intent
import android.content.IntentSender
import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.UserRepository
import com.clastic.model.authentication.AuthenticationResult
import com.clastic.model.authentication.AuthenticationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _authState = MutableStateFlow(AuthenticationState())

    private val _emailInput = MutableStateFlow("")
    val emailInput = _emailInput.asStateFlow()

    private val _passInput = MutableStateFlow("")
    val passInput = _passInput.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun setEmailInput(newEmail: String) { _emailInput.value = newEmail }

    fun setPasswordInput(newPassword: String) { _passInput.value = newPassword }

    fun setIsLoading(isLoading: Boolean) { _isLoading.value = isLoading }

    fun loginWithGoogle(onIntentSenderReceived: (IntentSender?) -> Unit) {
        _isLoading.value = true
        userRepository.userGoogleSignIn(onIntentSenderReceived)
    }

    fun getGoogleSignInResultFromIntent(
        intent: Intent,
        onSignInSuccess: () -> Unit,
        onSignInFailed: (String?) -> Unit
    ) {
        userRepository.getGoogleSignInResultFromIntent(
            intent,
            onSignInResult = { result ->
                onSignInResult(
                    result,
                    onSignInSuccess,
                    onSignInFailed
                )
            },
            onFetchFailed = onSignInFailed
        )
    }

    private fun onSignInResult(
        result: AuthenticationResult,
        onSignInSuccess: () -> Unit,
        onSignInFailed: (String?) -> Unit
    ) {
        _authState.update { it.copy(
            isAuthSuccessful = result.data != null,
            authError = result.errorMessage
        ) }
        if (_authState.value.isAuthSuccessful) { onSignInSuccess() }
        else { onSignInFailed(_authState.value.authError) }
    }

    fun resetAuthState() { _authState.update { AuthenticationState() } }
}