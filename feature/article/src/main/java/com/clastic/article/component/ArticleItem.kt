package com.clastic.article.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.clastic.model.Article
import com.clastic.ui.RecycleTag
import com.clastic.ui.theme.ClasticTheme

@Composable
fun ArticleItem(
    article: Article,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(175.dp)
            .clip(RoundedCornerShape(10.dp))
            .padding(8.dp)
            .clickable { onClick(article.contentUrl) }
    ) {
        Card(
            modifier = modifier,
            elevation = 10.dp
        ) {
            AsyncImage(
                model = article.posterUrl,
                contentDescription = article.title,
                modifier = Modifier.blur(1.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
            RecycleTag(tag = article.tag)
            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = Modifier
            ) {
                ArticleData(article = article)
            }
        }
    }
}

@Preview
@Composable
fun ArticleItemPreview() {
    ClasticTheme {
        ArticleItem(
            Article(
                title = "Title",
                posterUrl = "",
                author = "Author",
                tag = listOf("PP, HDPE, PET"),
                createdAt = "20-10-2000",
                contentUrl = ""
            ),
            onClick = {}
        )
    }
}