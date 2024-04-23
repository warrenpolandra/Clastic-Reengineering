package com.clastic.model.transaction.plastic

import com.google.errorprone.annotations.Keep

@Keep
data class PlasticTransactionItem(
    val plasticType: String = "",
    val weight: Float = 0f,
    val points: Int = 0
)