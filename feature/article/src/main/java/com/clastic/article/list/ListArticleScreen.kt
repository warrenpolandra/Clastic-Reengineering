package com.clastic.article.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.clastic.model.Article
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary

@Composable
fun ListArticleScreen(
    onArticleClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListArticleViewModel = hiltViewModel<ListArticleViewModel>()
) {
    ListArticleScreenContent(
        modifier = modifier,
        articleList = emptyList()
    )
}

@Composable
fun ListArticleScreenContent(
    articleList: List<Article>,
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
        },
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
                    //ListArticle(article = article, onClick = onClick)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListArticleScreenPreview() {
    ClasticTheme {
        ListArticleScreenContent(
            articleList = emptyList()
        )
    }
}