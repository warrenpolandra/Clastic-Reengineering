package com.clastic.article.detail

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.clastic.article.R
import com.clastic.ui.ClasticTopAppBar

@Composable
fun ArticleDetailScreen(
    contentUrl: String,
    navigateToListArticle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ClasticTopAppBar(
                title = stringResource(R.string.article),
                onBackPressed = navigateToListArticle
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        AndroidView(
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    loadUrl(contentUrl)
                }
            },
            update = {
                it.loadUrl(contentUrl)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}