package com.clastic.article.component

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.clastic.model.Article
import com.clastic.ui.RecycleTag
import com.clastic.ui.theme.ClasticTheme
import com.clastic.ui.theme.CyanPrimary
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
internal fun ArticleItem(
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
            .background(CyanPrimary)
            .clickable {
                val encodedArticleUrl = URLEncoder.encode(
                    article.contentUrl,
                    StandardCharsets.UTF_8.toString()
                )
                onClick(encodedArticleUrl)
            }
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(175.dp)
                .clip(RoundedCornerShape(10.dp))
                .padding(8.dp),
            elevation = 10.dp
        ) {
            AsyncImage(
                model = article.posterUrl,
                contentDescription = article.title,
                modifier = modifier
                    .fillMaxWidth()
                    .height(175.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .padding(8.dp)
                    .blur(1.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
            RecycleTag(
                tag = article.tag,
                modifier = Modifier.padding(top = 10.dp, start = 10.dp)
            )
            Box(
                contentAlignment = Alignment.BottomStart,
            ) {
                ArticleData(article = article)
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun ArticleItemPreview() {
    ClasticTheme {
        ArticleItem(
            Article(
                title = "Berbagai Macam Bisnis Daur Ulang Sampah Mendulang Rupiah yang Menjanjikan",
                posterUrl = "https://firebasestorage.googleapis.com/v0/b/clastic-rebuild.appspot.com/o/Article%2FArticle_4.jpg?alt=media&token=254d87f7-67a7-4701-b995-cc87ae213da3",
                author = "Webmaster",
                tag = listOf("PP, HDPE, PET"),
                createdAt = "20-10-2000",
                contentUrl = "https://dlh.semarangkota.go.id/berbagai-macam-bisnis-daur-ulang-sampah-mendulang-rupiah-yang-menjanjikan/"
            ),
            onClick = {}
        )
    }
}