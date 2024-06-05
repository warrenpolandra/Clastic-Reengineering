package com.clastic.data.repository

import com.clastic.domain.repository.PlasticTransactionRepository
import com.clastic.model.transaction.plastic.PlasticTransaction
import com.clastic.model.transaction.plastic.PlasticTransactionItem
import com.clastic.utils.TimeUtil
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date
import javax.inject.Inject

class PlasticTransactionRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
): PlasticTransactionRepository {
    override fun submitPlasticTransaction(
        date: Date,
        dropPointId: String,
        ownerId: String,
        totalPoints: Long,
        userId: String,
        plasticTransactionItemList: List<PlasticTransactionItem>,
        onPostSuccess: (transactionId: String) -> Unit,
        onPostFailed: (String) -> Unit
    ) {
        db.collection("user").document(ownerId).get()
            .addOnSuccessListener { document ->
                val ownerPoints = document.getLong("points") ?: 0
                if (ownerPoints < totalPoints) {
                    onPostFailed("Not enough owner points")
                    return@addOnSuccessListener
                }

                val batch = db.batch()
                val userRef = db.collection("user").document(userId)
                val ownerRef = db.collection("user").document(ownerId)
                val transactionRef = db.collection("plasticTransaction").document()

                batch.update(userRef, "totalTransaction", FieldValue.increment(1))
                batch.update(ownerRef, "totalTransaction", FieldValue.increment(1))
                batch.update(userRef, "points", FieldValue.increment(totalPoints))
                batch.update(ownerRef, "points", FieldValue.increment(-totalPoints))

                val totalWeight = plasticTransactionItemList.sumOf { it.weight.toDouble() }
                batch.update(userRef, "totalPlastic", FieldValue.increment(totalWeight))
                batch.update(ownerRef, "totalPlastic", FieldValue.increment(totalWeight))

                val newTransaction = hashMapOf(
                    "id" to transactionRef.id,
                    "date" to TimeUtil.dateToTimestamp(date),
                    "dropPointId" to dropPointId,
                    "ownerId" to ownerId,
                    "plasticList" to getPlasticTransactionMap(plasticTransactionItemList),
                    "totalPoints" to totalPoints,
                    "userId" to userId,
                )
                batch.set(transactionRef, newTransaction)

                batch.update(userRef, "plasticTransactionList", FieldValue.arrayUnion(transactionRef.id))
                batch.update(ownerRef, "plasticTransactionList", FieldValue.arrayUnion(transactionRef.id))

                batch.commit()
                    .addOnSuccessListener { onPostSuccess(transactionRef.id) }
                    .addOnFailureListener { error -> onPostFailed(error.message.toString()) }
            }
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

    @Suppress("UNCHECKED_CAST")
    override fun getPlasticTransactionByUserId(
        userId: String,
        onFetchSuccess: (List<PlasticTransaction>) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        db.collection("user").document(userId).get()
            .addOnSuccessListener { document ->
                val plasticTransactionList = mutableListOf<PlasticTransaction>()
                val plasticTransactionListId = document.get("plasticTransactionList") as List<String>
                if (plasticTransactionListId.isEmpty()) {
                    onFetchSuccess(emptyList())
                } else {
                    plasticTransactionListId.forEach { plasticTransactionId ->
                        getPlasticTransactionById(
                            plasticTransactionId = plasticTransactionId,
                            onFetchSuccess = { plasticTransaction ->
                                plasticTransactionList.add(plasticTransaction)
                                if (plasticTransactionList.size == plasticTransactionListId.size) {
                                    onFetchSuccess(plasticTransactionList)
                                }
                            },
                            onFetchFailed = onFetchFailed
                        )
                    }
                }
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