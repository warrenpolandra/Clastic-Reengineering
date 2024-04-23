package com.clastic.domain.repository

import com.clastic.model.transaction.plastic.PlasticTransaction
import com.clastic.model.transaction.plastic.PlasticTransactionItem
import java.util.Date

interface PlasticTransactionRepository {
    fun submitPlasticTransaction(
        date: Date,
        dropPointId: String,
        ownerId: String,
        totalPoints: Int,
        userId: String,
        plasticTransactionItemList: List<PlasticTransactionItem>,
        onPostSuccess: (transactionId: String) -> Unit,
        onPostFailed: (String) -> Unit
    )

    fun getPlasticTransactionById(
        plasticTransactionId: String,
        onFetchSuccess: (PlasticTransaction) -> Unit,
        onFetchFailed: (String) -> Unit
    )
}