package com.clastic.data.repository

import com.clastic.domain.repository.PlasticKnowledgeRepository
import com.clastic.model.PlasticKnowledge
import com.clastic.model.PlasticProduct
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class PlasticKnowledgeRepositoryImpl @Inject constructor(): PlasticKnowledgeRepository {
    private val db = FirebaseFirestore.getInstance()

    override fun fetchListPlasticKnowledge(
        onFetchSuccess: (List<PlasticKnowledge>) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        val plasticKnowledgeList = mutableListOf<PlasticKnowledge>()
        db.collection("plasticKnowledge").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    plasticKnowledgeList.add(getPlasticKnowledgeFromDocument(document))
                }
                onFetchSuccess(plasticKnowledgeList)
            }
            .addOnFailureListener { e ->
                onFetchFailed(e.message.toString())
            }
    }

    override fun fetchPlasticKnowledgeById(
        plasticId: String,
        onFetchSuccess: (plasticKnowledge: PlasticKnowledge) -> Unit,
        onFetchFailed: (error: String) -> Unit
    ) {
        db.collection("plasticKnowledge").document("PK-$plasticId").get()
            .addOnSuccessListener { document ->
                onFetchSuccess(getPlasticKnowledgeFromDocument(document))
            }
            .addOnFailureListener { e ->
                onFetchFailed(e.message.toString())
            }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getPlasticKnowledgeFromDocument(document: DocumentSnapshot): PlasticKnowledge {
        return PlasticKnowledge(
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
    }
}