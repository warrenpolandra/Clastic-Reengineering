package com.clastic.data.repository

import com.clastic.domain.repository.PlasticKnowledgeRepository
import com.clastic.model.PlasticKnowledge
import com.clastic.model.PlasticProduct
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class PlasticKnowledgeRepositoryImpl @Inject constructor(): PlasticKnowledgeRepository {
    private val db = FirebaseFirestore.getInstance()

    @Suppress("UNCHECKED_CAST")
    override fun fetchListPlasticKnowledge(
        onFetchSuccess: (List<PlasticKnowledge>) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        val plasticKnowledgeList = mutableListOf<PlasticKnowledge>()
        db.collection("plasticKnowledge").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    plasticKnowledgeList.add(
                        PlasticKnowledge(
                            tag = document.getString("tag") ?: "",
                            name = document.getString("name") ?: "",
                            description = document.getString("description") ?: "",
                            colorHex = document.getString("color") ?: "",
                            coverUrl = document.getString("coverUrl") ?: "",
                            logoUrl = document.getString("logoUrl") ?: "",
                            productList = (document.get("productList") as List<Map<String, String>>).map { product ->
                                PlasticProduct(
                                    name = product["productName"] as String,
                                    imageUrl = product["productImageUrl"] as String
                                )
                            }
                        )
                    )
                }
                onFetchSuccess(plasticKnowledgeList)
            }
            .addOnFailureListener { e ->
                onFetchFailed(e.message.toString())
            }
    }
}