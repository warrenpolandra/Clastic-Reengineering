package com.clastic.article.mock

import com.clastic.model.Article

class MockExtendedArticleRepository(
    private val article: Article,
    private val isSuccess: Boolean,
    private val errorMessage: String
): MockArticleRepository(errorMessage, listOf(article), isSuccess), ExtendedArticleRepository {

    override fun fetchArticleById(
        articleId: String,
        onFetchSuccess: (Article) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        if (isSuccess) { onFetchSuccess(article) }
        else { onFetchFailed(errorMessage) }
    }
}