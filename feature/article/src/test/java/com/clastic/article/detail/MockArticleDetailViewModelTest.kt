package com.clastic.article.detail

import com.clastic.article.mock.MockArticleDetailViewModel
import com.clastic.article.mock.MockExtendedArticleRepository
import com.clastic.model.Article
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import org.junit.Before
import org.junit.Test

class MockArticleDetailViewModelTest {
    private val article = Article(
        title = "SAMPLE_TITLE",
        posterUrl = "SAMPLE_POSTER_URL",
        author = "SAMPLE_AUTHOR",
        tag = listOf("SAMPLE_TAG"),
        contentUrl = "SAMPLE_URL",
        createdAt = "SAMPLE_TIMESTAMP"
    )
    private val articleId = "SAMPLE_ID"
    private val errorMessage = "RETURN FAIL"
    private lateinit var viewModel: MockArticleDetailViewModel
    private lateinit var articleRepository: MockExtendedArticleRepository

    @Before
    fun setUp() {
        articleRepository = MockExtendedArticleRepository(article, true, errorMessage)
        viewModel = MockArticleDetailViewModel(articleRepository)
    }

    fun `should not access other method than fetchArticleById`() {
        viewModel.fetchArticleById(
            articleId,
            onFetchSuccess = { data ->
                assertEquals(article, data)
            },
            onFetchFailed = { fail(it) }
        )
    }

    fun `should return fail when fetchArticleById is used`() {
        articleRepository = MockExtendedArticleRepository(article, false, errorMessage)
        viewModel = MockArticleDetailViewModel(articleRepository)
        viewModel.fetchArticleById(
            articleId,
            onFetchSuccess = { fail("Fetch should be failed, instead succeeded") },
            onFetchFailed = { assertEquals(errorMessage, it) }
        )
    }

    @Test
    fun `should access ArticleRepository method from ExtendedArticleRepository`() {
        articleRepository = MockExtendedArticleRepository(article, true, errorMessage)
        viewModel = MockArticleDetailViewModel(articleRepository)
        viewModel.fetchArticles(
            onFetchSuccess = { listArticle ->
                assertEquals(listOf(article), listArticle)
            },
            onFetchFailed = { fail(it) }
        )
    }
}