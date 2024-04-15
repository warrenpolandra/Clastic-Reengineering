package com.clastic.domain.repository

import com.clastic.model.PlasticKnowledge

interface PlasticKnowledgeRepository {
    fun fetchListPlasticKnowledge(
        onFetchSuccess: (List<PlasticKnowledge>) -> Unit,
        onFetchFailed: (String) -> Unit
    )
}