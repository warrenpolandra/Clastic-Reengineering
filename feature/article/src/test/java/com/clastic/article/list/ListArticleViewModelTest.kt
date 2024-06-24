package com.clastic.article.list

import com.clastic.article.mock.MockArticleRepository
import com.clastic.model.Article
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ListArticleViewModelTest {
    private lateinit var viewModel: ListArticleViewModel
    private lateinit var articleRepository: MockArticleRepository
    private val articleData = listOf(
        Article(
        "SAMPLE_TITLE",
        "SAMPLE_POSTER_URL",
        "SAMPLE_AUTHOR",
        listOf("TAG1", "TAG2"),
        "SAMPLE_TIMESTAMP",
        "SAMPLE_CONTENT_URL"
    ))
    private val errorMessage = "Returned Fetch Failed"

    @Before
    fun setUp() {
        articleRepository = MockArticleRepository("", articleData, true)
        viewModel = ListArticleViewModel(articleRepository)
    }

    @Test
    fun `should return list of article when fetchArticles is used`() {
        viewModel.fetchListArticle()
        assertEquals(
            listOf(Article(
                "SAMPLE_TITLE",
                "SAMPLE_POSTER_URL",
                "SAMPLE_AUTHOR",
                listOf("TAG1", "TAG2"),
                "SAMPLE_TIMESTAMP",
                "SAMPLE_CONTENT_URL"
            )), viewModel.articleList.value
        )
    }

    @Test
    fun `should return error message when fetchArticles is used`() {
        articleRepository = MockArticleRepository(errorMessage, articleData, false)
        viewModel = ListArticleViewModel(articleRepository)
        viewModel.fetchListArticle()
        assertEquals("Returned Fetch Failed", viewModel.errorMessage.value)
    }
}