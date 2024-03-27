package com.clastic.domain.repository

import com.clastic.model.Article

interface ArticleRepository {
    fun fetchArticles(
        onFetchSuccess: (List<Article>) -> Unit,
        onFetchFailed: (String) -> Unit
    )
}