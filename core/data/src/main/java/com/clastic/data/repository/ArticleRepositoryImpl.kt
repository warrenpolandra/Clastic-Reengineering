package com.clastic.data.repository

import com.clastic.domain.repository.ArticleRepository
import com.clastic.model.Article
import com.clastic.utils.TimeUtil.toDateFormat
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
): ArticleRepository {

    @Suppress("UNCHECKED_CAST")
    override fun fetchArticles(
        onFetchSuccess: (List<Article>) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        val articleList = mutableListOf<Article>()
        db.collection("article").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    articleList.add(
                        Article(
                            title = document.getString("title") ?: "",
                            posterUrl = document.getString("posterUrl") ?: "",
                            author = document.getString("author") ?: "",
                            tag = document.get("tag") as List<String>,
                            createdAt = document.getTimestamp("createdAt")?.toDateFormat() ?: "",
                            contentUrl = document.getString("contentUrl") ?: ""
                        )
                    )
                }
                onFetchSuccess(articleList)
            }
            .addOnFailureListener { e ->
                onFetchFailed(e.message.toString())
            }
    }
}