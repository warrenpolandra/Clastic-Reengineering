package com.clastic.article.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.article.component.ArticleItem
import com.clastic.model.Article
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary

@Composable
fun ListArticleScreen(
    onArticleClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: ListArticleViewModel = hiltViewModel<ListArticleViewModel>()
    val articleList by viewModel.articleList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.errorMessage.collectAsState()

    ListArticleScreenContent(
        isLoading = isLoading,
        error = error,
        articleList = articleList,
        onArticleClick = onArticleClick,
        modifier = modifier,
    )
}

@Composable
private fun ListArticleScreenContent(
    isLoading: Boolean,
    error: String,
    articleList: List<Article>,
    onArticleClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Article",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 15.dp)
                    )
                },
                backgroundColor = CyanPrimary
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            LazyColumn(state = rememberLazyListState()) {
                items(
                    articleList,
                    key = {it.title}
                ) { article->
                    ArticleItem(
                        article = article,
                        onClick = onArticleClick
                    )
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(64.dp)
                )
            }
            if (error.isNotEmpty()) {
                Text(
                    text = error,
                )
            }
       }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListArticleScreenPreview() {
    ClasticTheme {
        ListArticleScreenContent(
            isLoading = true,
            error = "Error getting data",
            articleList = emptyList(),
            onArticleClick = {}
        )
    }
}