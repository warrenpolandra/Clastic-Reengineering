package com.clastic.data.repository

import com.clastic.domain.repository.PlasticTransactionRepository
import com.clastic.model.transaction.plastic.PlasticTransaction
import com.clastic.model.transaction.plastic.PlasticTransactionItem
import com.clastic.utils.TimeUtil
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date
import javax.inject.Inject

class PlasticTransactionRepositoryImpl @Inject constructor(): PlasticTransactionRepository {
    private val db = FirebaseFirestore.getInstance()

    override fun submitPlasticTransaction(
        date: Date,
        dropPointId: String,
        ownerId: String,
        totalPoints: Int,
        userId: String,
        plasticTransactionItemList: List<PlasticTransactionItem>,
        onPostSuccess: (transactionId: String) -> Unit,
        onPostFailed: (String) -> Unit
    ) {
        val newDocumentRef = db.collection("plasticTransaction").document()
        val newTransaction = hashMapOf(
            "id" to newDocumentRef.id,
            "date" to TimeUtil.dateToTimestamp(date),
            "dropPointId" to dropPointId,
            "ownerId" to ownerId,
            "plasticList" to getPlasticTransactionMap(plasticTransactionItemList),
            "totalPoints" to totalPoints,
            "userId" to userId,
        )
        newDocumentRef.set(newTransaction)
            .addOnSuccessListener { onPostSuccess(newDocumentRef.id) }
            .addOnFailureListener { error -> onPostFailed(error.message.toString()) }
    }

    override fun getPlasticTransactionById(
        plasticTransactionId: String,
        onFetchSuccess: (PlasticTransaction) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        db.collection("plasticTransaction").document(plasticTransactionId).get()
            .addOnSuccessListener { document ->
                val plasticList = mutableListOf<PlasticTransactionItem>()
                val plasticListData = document.get("plasticList") as Map<*, *>
                plasticListData.forEach { (plasticType, data) ->
                    val points = (data as Map<*, *>)["points"] as Long
                    val weight = data["weight"] as Double
                    plasticList.add(
                        PlasticTransactionItem(
                            plasticType.toString(),
                            weight.toFloat(),
                            points.toInt()
                        )
                    )
                }
                onFetchSuccess(
                    PlasticTransaction(
                        id = document.getString("id") ?: "",
                        date = document.getTimestamp("date") ?: Timestamp.now(),
                        dropPointId = document.getString("dropPointId") ?: "",
                        ownerId = document.getString("ownerId") ?: "",
                        userId = document.getString("userId") ?: "",
                        plasticList = plasticList,
                        totalPoints = document.getLong("totalPoints")?.toInt() ?: 0
                    )
                )
            }
            .addOnFailureListener { error -> onFetchFailed(error.message.toString()) }
    }

    private fun getPlasticTransactionMap(
        plasticTransactionItemList: List<PlasticTransactionItem>
    ): Map<String, Map<String, Any>> {
        val plasticTransactionMap = mutableMapOf<String, Map<String, Any>>()

        for (item in plasticTransactionItemList) {
            val innerMap = mapOf(
                "weight" to item.weight,
                "points" to item.points
            )
            plasticTransactionMap[item.plasticType] = innerMap
        }
        return plasticTransactionMap
    }
}