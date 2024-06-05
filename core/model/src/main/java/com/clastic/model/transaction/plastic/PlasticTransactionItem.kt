package com.clastic.model.transaction.plastic

import androidx.annotation.Keep

@Keep
data class PlasticTransactionItem(
    val plasticType: String = "",
    val weight: Float = 0f,
    val points: Int = 0
)