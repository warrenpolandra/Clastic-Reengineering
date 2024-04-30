package com.clastic.plastic_knowledge

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.PlasticKnowledgeRepository
import com.clastic.model.PlasticKnowledge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class PlasticKnowledgeViewModel @Inject constructor(
    private val plasticKnowledgeRepository: PlasticKnowledgeRepository
): ViewModel() {
    private val _plasticKnowledge = MutableStateFlow(PlasticKnowledge())
    val plasticKnowledge = _plasticKnowledge.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun fetchPlasticKnowledgeById(
        plasticId: String,
        onFetchFailed: (error: String) -> Unit
    ) {
        plasticKnowledgeRepository.fetchPlasticKnowledgeById(
            plasticId = plasticId,
            onFetchSuccess = { plasticKnowledge ->
                _plasticKnowledge.value = plasticKnowledge
            },
            onFetchFailed = onFetchFailed
        )
    }
}