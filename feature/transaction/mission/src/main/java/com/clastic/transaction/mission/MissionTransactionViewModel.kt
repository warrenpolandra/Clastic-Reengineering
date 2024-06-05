package com.clastic.transaction.mission

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.MissionRepository
import com.clastic.domain.repository.UserRepository
import com.clastic.model.Mission
import com.clastic.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class MissionTransactionViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val missionRepository: MissionRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Mission>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _linkSubmission = MutableStateFlow("")
    val linkSubmission = _linkSubmission.asStateFlow()

    private val _linkSubmissionEnabled = MutableStateFlow(true)
    val linkSubmissionEnabled = _linkSubmissionEnabled.asStateFlow()

    private val _buttonEnabled = MutableStateFlow(false)
    val buttonEnabled = _buttonEnabled.asStateFlow()

    private val _isTnCChecked = MutableStateFlow(false)
    val isTnCChecked = _isTnCChecked.asStateFlow()

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri = _imageUri.asStateFlow()

    private val _imageSubmissionEnabled = MutableStateFlow(true)
    val imageSubmissionEnabled = _imageSubmissionEnabled.asStateFlow()

    fun fetchMissionById(missionId: String) {
        missionRepository.fetchMissionById(
            missionId = missionId,
            onFetchSuccess = { mission -> _uiState.value = UiState.Success(mission) },
            onFetchFailed = { error -> _uiState.value = UiState.Error(error) }
        )
    }

    fun setLinkSubmission(newValue: String) {
        _linkSubmission.value = newValue
        _imageSubmissionEnabled.value = newValue.isEmpty()
        setButtonEnabled()
    }

    fun setImageUri(newValue: Uri?) {
        _imageUri.value = newValue
        _linkSubmissionEnabled.value = newValue == null
        setButtonEnabled()
    }

    fun setTnCChecked() {
        _isTnCChecked.value = !_isTnCChecked.value
        setButtonEnabled()
    }

    fun submitMission(
        missionId: String,
        imageUri: Uri?,
        linkSubmission: String,
        totalPoints: Int,
        onPostSuccess: (transactionId: String) -> Unit,
        onPostFailed: (error: String) -> Unit
    ) {
        _isLoading.value = true
        if (imageUri != null) {
            userRepository.getUserInfo(
                onFetchSuccess = { user ->
                    missionRepository.postSubmitMission(
                        userId = user.email,
                        missionId = missionId,
                        imageUri = imageUri,
                        totalPoints = totalPoints,
                        onPostSuccess = {
                            _isLoading.value = false
                            onPostSuccess(it)
                        },
                        onPostFailed = {
                            _isLoading.value = false
                            onPostFailed(it)
                        }
                    )
                },
                onFetchFailed = {
                    _isLoading.value = false
                    onPostFailed(it)
                }
            )
        } else {
            userRepository.getUserInfo(
                onFetchSuccess = { user ->
                    missionRepository.postSubmitMission(
                        userId = user.email,
                        missionId = missionId,
                        linkSubmission = linkSubmission,
                        totalPoints = totalPoints,
                        onPostSuccess = {
                            _isLoading.value = false
                            onPostSuccess(it)
                        },
                        onPostFailed = {
                            _isLoading.value = false
                            onPostFailed(it)
                        }
                    )
                },
                onFetchFailed = {
                    _isLoading.value = false
                    onPostFailed(it)
                }
            )
        }
    }


    private fun setButtonEnabled() {
        _buttonEnabled.value = (_imageUri.value != null && _isTnCChecked.value) || (_linkSubmission.value.isNotEmpty() && _isTnCChecked.value)
    }
}