package com.clastic.data

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    fun getTimestamp() = Timestamp.now()

    fun Timestamp.toDateFormat(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(this.toDate())
    }
}