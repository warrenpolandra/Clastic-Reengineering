package com.clastic.model

import androidx.annotation.Keep

@Keep
data class Impact(
    val key: String,
    val value: String,
    val image: Int
)
