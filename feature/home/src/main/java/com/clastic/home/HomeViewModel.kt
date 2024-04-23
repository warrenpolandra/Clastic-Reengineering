package com.clastic.home

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.PlasticKnowledgeRepository
import com.clastic.domain.repository.UserRepository
import com.clastic.model.PlasticKnowledge
import com.clastic.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val plasticKnowledgeRepository: PlasticKnowledgeRepository
): ViewModel() {
    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private val _listPlasticKnowledge = MutableStateFlow<List<PlasticKnowledge>>(emptyList())
    val listPlasticKnowledge = _listPlasticKnowledge.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        fetchUserInfo()
        fetchListPlasticKnowledge()
    }

    private fun fetchListPlasticKnowledge() {
        _isLoading.value = true
        plasticKnowledgeRepository.fetchListPlasticKnowledge(
            onFetchSuccess = { listPlasticKnowledge ->
                _listPlasticKnowledge.value = listPlasticKnowledge
            },
            onFetchFailed = {
                _listPlasticKnowledge.value = emptyList()
            }
        )
    }

    private fun fetchUserInfo() {
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
}