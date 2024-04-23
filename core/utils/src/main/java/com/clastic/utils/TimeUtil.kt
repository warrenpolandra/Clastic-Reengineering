package com.clastic.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object TimeUtil {
    fun getTimestamp() = Timestamp.now()

    fun Timestamp.toDateFormat(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(this.toDate())
    }

    fun getCurrentTimeSeconds() = Timestamp.now().seconds

    fun getRemainingDays(endTimeInSeconds: Long): Int {
        val currentTimeMillis = System.currentTimeMillis() / 1000
        val remainingSeconds = endTimeInSeconds - currentTimeMillis
        return TimeUnit.SECONDS.toDays(remainingSeconds).toInt()
    }

    fun getCurrentDateTime(): Date = Timestamp.now().toDate()

    fun dateToStringFormat(date: Date): String {
        val formatter = SimpleDateFormat("EEEE, dd MMMM yyyy 'jam' HH:mm:ss",
            Locale("id", "ID")
        )
        return formatter.format(date)
    }

    fun timestampToStringFormat(timestamp: Timestamp): String {
        val formatter = SimpleDateFormat("EEEE, dd MMMM yyyy 'jam' HH:mm:ss",
            Locale("id", "ID")
        )
        return formatter.format(timestamp.toDate())
    }

    fun dateToTimestamp(date: Date): Timestamp = Timestamp(date)
}