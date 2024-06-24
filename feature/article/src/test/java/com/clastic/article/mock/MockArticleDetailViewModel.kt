package com.clastic.article.mock

import com.clastic.model.Article

class MockArticleDetailViewModel(
    private val articleRepository: ExtendedArticleRepository
) {
    fun fetchArticles(
        onFetchSuccess: (List<Article>) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        articleRepository.fetchArticles(onFetchSuccess, onFetchFailed)
    }

    fun fetchArticleById(
        articleId: String,
        onFetchSuccess: (Article) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        articleRepository.fetchArticleById(articleId, onFetchSuccess, onFetchFailed)
    }
}