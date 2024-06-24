package com.clastic.article.mock

import com.clastic.domain.repository.ArticleRepository
import com.clastic.model.Article

interface ExtendedArticleRepository: ArticleRepository {
    fun fetchArticleById(
        articleId: String,
        onFetchSuccess: (Article) -> Unit,
        onFetchFailed: (String) -> Unit
    )
}