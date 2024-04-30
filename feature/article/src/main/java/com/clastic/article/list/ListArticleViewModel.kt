package com.clastic.article.list

import androidx.lifecycle.ViewModel
import com.clastic.domain.repository.ArticleRepository
import com.clastic.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class ListArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
): ViewModel() {
    private val _articleList = MutableStateFlow<List<Article>>(emptyList())
    val articleList = _articleList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    init {
        fetchListArticle()
    }

    private fun fetchListArticle() {
        _errorMessage.value = ""
        _isLoading.value = true
        articleRepository.fetchArticles(
            onFetchSuccess = { articleList ->
                _articleList.value = articleList.sortedBy { it.title }
                _isLoading.value = false
            },
            onFetchFailed = { error ->
                _isLoading.value = false
                _errorMessage.value = error
            }
        )
    }
}