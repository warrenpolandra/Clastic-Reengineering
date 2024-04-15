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

    init {
        fetchListPlasticKnowledge()
    }

    private fun fetchListPlasticKnowledge() {
        plasticKnowledgeRepository.fetchListPlasticKnowledge(
            onFetchSuccess = { listPlasticKnowledge ->
                _listPlasticKnowledge.value = listPlasticKnowledge
            },
            onFetchFailed = {/*TODO*/}
        )
    }
}