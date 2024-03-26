package com.clastic.model

import androidx.annotation.Keep

@Keep
data class Article(
    val title: String,
    val posterUrl: String,
    val author: String,
    val tag: List<String>,
    val createdAt: String,
    val contentUrl: String
)
