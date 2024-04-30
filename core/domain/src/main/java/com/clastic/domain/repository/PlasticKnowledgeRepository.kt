package com.clastic.domain.repository

import com.clastic.model.PlasticKnowledge

interface PlasticKnowledgeRepository {
    fun fetchListPlasticKnowledge(
        onFetchSuccess: (List<PlasticKnowledge>) -> Unit,
        onFetchFailed: (String) -> Unit
    )

    fun fetchPlasticKnowledgeById(
        plasticId: String,
        onFetchSuccess: (plasticKnowledge: PlasticKnowledge) -> Unit,
        onFetchFailed: (error: String) -> Unit
    )
}