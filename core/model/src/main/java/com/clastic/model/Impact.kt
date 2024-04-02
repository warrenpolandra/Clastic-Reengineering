package com.clastic.model

import androidx.annotation.Keep

@Keep
data class Impact(
    val numberValue: Int,
    val description: String,
    val imageUrl: String
)
