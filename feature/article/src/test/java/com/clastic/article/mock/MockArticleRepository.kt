package com.clastic.article.mock

import com.clastic.domain.repository.ArticleRepository
import com.clastic.model.Article

open class MockArticleRepository(
    private val errorMessage: String,
    private val articleData: List<Article>,
    private val isSuccess: Boolean
): ArticleRepository {
    override fun fetchArticles(
        onFetchSuccess: (List<Article>) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        if (isSuccess) { onFetchSuccess(articleData) }
        else { onFetchFailed(errorMessage) }
    }
}