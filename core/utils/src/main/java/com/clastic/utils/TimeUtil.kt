package com.clastic.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

object TimeUtil {
    fun getTimestamp() = Timestamp.now()

    fun Timestamp.toDateFormat(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", java.util.Locale.getDefault())
        return dateFormat.format(this.toDate())
    }

    fun getCurrentTimeSeconds() = Timestamp.now().seconds

    fun getRemainingDays(endTimeInSeconds: Long): Int {
        val currentTimeMillis = System.currentTimeMillis() / 1000
        val remainingSeconds = endTimeInSeconds - currentTimeMillis
        return TimeUnit.SECONDS.toDays(remainingSeconds).toInt()
    }
}