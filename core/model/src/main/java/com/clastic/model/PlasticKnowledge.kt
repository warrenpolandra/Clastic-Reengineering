package com.clastic.model

import androidx.annotation.Keep

@Keep
data class PlasticKnowledge(
    val tag: String,
    val name: String,
    val description: String,
    val colorHex: String,
    val coverUrl: String,
    val logoUrl: String,
    val productList: List<PlasticProduct>
)

@Keep
data class PlasticProduct(
    val name: String,
    val imageUrl: String
)