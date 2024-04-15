package com.clastic.model

import com.google.errorprone.annotations.Keep

@Keep
data class DropPoint(
    val id: String,
    val lat: Double,
    val long: Double,
    val name: String,
    val address: String,
    val ownerEmail: String
)