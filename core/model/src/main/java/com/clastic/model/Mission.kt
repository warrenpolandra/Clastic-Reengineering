package com.clastic.model

import androidx.annotation.Keep

@Keep
data class Mission(
    val title: String,
    val description: String,
    val image: Int,
    val tag: String,
    val reward: Int,
    val listImpact: List<Impact>
)
