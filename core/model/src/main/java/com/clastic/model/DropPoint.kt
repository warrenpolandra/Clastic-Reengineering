package com.clastic.model

import com.google.errorprone.annotations.Keep

@Keep
data class DropPoint(
    val id: String = "",
    val lat: Double = 0.0,
    val long: Double = 0.0,
    val name: String = "",
    val address: String = "",
    val ownerEmail: String = ""
)