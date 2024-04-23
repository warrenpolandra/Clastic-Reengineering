package com.clastic.data.repository

import com.clastic.domain.repository.DropPointRepository
import com.clastic.model.DropPoint
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class DropPointRepositoryImpl @Inject constructor(): DropPointRepository {
    private val db = Firebase.firestore

    override fun getDropPointList(
        onFetchSuccess: (List<DropPoint>, LatLng) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        val dropPointList = mutableListOf<DropPoint>()

        db.collection("dropPoint").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val coordinate = document.getGeoPoint("coordinate")
                    dropPointList.add(
                        DropPoint(
                            id = document.getString("id") ?: "",
                            lat = coordinate?.latitude ?: 0.0,
                            long = coordinate?.longitude ?: 0.0,
                            name = document.getString("name") ?: "",
                            address = document.getString("address") ?: "",
                            ownerEmail = document.getString("owner") ?: ""
                        )
                    )
                }
                val positions = mutableListOf<LatLng>()
                for (dropPoint in dropPointList) {
                    positions.add(LatLng(dropPoint.lat, dropPoint.long))
                }
                val boundsCenter = getBoundsCenter(positions)
                onFetchSuccess(dropPointList.sortedBy { it.id }, boundsCenter)
            }
            .addOnFailureListener { error ->
                onFetchFailed(error.message.toString())
            }
    }

    override fun getDropPointByOwnerId(
        ownerId: String,
        onFetchSuccess: (DropPoint) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        db.collection("dropPoint")
            .whereEqualTo("owner", ownerId)
            .get()
            .addOnSuccessListener { snapshot ->
                if (!snapshot.isEmpty) {
                    val document = snapshot.documents.first()
                    val dropPoint =  DropPoint(
                        id = document.getString("id") ?: "",
                        lat = document.getGeoPoint("coordinate")?.latitude ?: 0.0,
                        long = document.getGeoPoint("coordinate")?.longitude ?: 0.0,
                        name = document.getString("name") ?: "",
                        address = document.getString("address") ?: "",
                        ownerEmail = document.getString("owner") ?: ""
                    )
                    onFetchSuccess(dropPoint)
                }
            }
            .addOnFailureListener { error ->
                onFetchFailed(error.message.toString())
            }
    }

    override fun getDropPointById(
        dropPointId: String,
        onFetchSuccess: (DropPoint) -> Unit,
        onFetchFailed: (String) -> Unit
    ) {
        db.collection("dropPoint").document(dropPointId).get()
            .addOnSuccessListener { document ->
                val dropPoint =  DropPoint(
                    id = document.getString("id") ?: "",
                    lat = document.getGeoPoint("coordinate")?.latitude ?: 0.0,
                    long = document.getGeoPoint("coordinate")?.longitude ?: 0.0,
                    name = document.getString("name") ?: "",
                    address = document.getString("address") ?: "",
                    ownerEmail = document.getString("owner") ?: ""
                )
                onFetchSuccess(dropPoint)
            }
            .addOnFailureListener { error ->
                onFetchFailed(error.message.toString())
            }
    }

    private fun getBoundsCenter(positions: List<LatLng>): LatLng {
        val boundBuilder = LatLngBounds.Builder()
        for (position in positions) {
            boundBuilder.include(position)
        }
        val bounds = boundBuilder.build()
        return LatLng(
            (bounds.southwest.latitude + bounds.northeast.latitude) / 2,
            (bounds.southwest.longitude + bounds.northeast.longitude) / 2
        )
    }
}