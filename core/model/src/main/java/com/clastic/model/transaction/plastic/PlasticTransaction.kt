package com.clastic.model.transaction.plastic

import com.google.errorprone.annotations.Keep
import com.google.firebase.Timestamp

@Keep
data class PlasticTransaction(
    val id: String = "",
    val date: Timestamp = Timestamp.now(),
    val dropPointId: String = "",
    val ownerId: String = "",
    val userId: String = "",
    val plasticList: List<PlasticTransactionItem> = emptyList(),
    val totalPoints: Int = 0
)