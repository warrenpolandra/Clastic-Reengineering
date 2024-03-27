package com.clastic.article.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clastic.article.R
import com.clastic.model.Article
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import com.clastic.ui.typhography.TextWithShadow
import com.clastic.ui.typhography.TitleWithShadow

@Composable
fun ArticleData(
    article: Article,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        TitleWithShadow(text = article.title)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_clock),
                tint = CyanPrimary,
                contentDescription = null,
            )
            TextWithShadow(
                text = article.createdAt,
                modifier = Modifier.padding(end = 10.dp),
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_person_white),
                contentDescription = null,
                tint = CyanPrimary
            )
            TextWithShadow(
                text = article.author,
                modifier = Modifier.padding(end = 10.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleDataPreview() {
    ClasticTheme {
        ArticleData(
            article = Article(
                title = "Title",
                posterUrl = "",
                author = "Author",
                tag = listOf("PP, HDPE, PET"),
                createdAt = "20-10-2000",
                contentUrl = ""
            )
        )
    }
}