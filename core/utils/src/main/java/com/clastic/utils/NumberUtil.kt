package com.clastic.utils

import java.text.NumberFormat
import java.util.Locale

object NumberUtil {
    fun formatNumberToLocale(number: Int): String {
        val numberFormat = NumberFormat.getInstance(Locale.getDefault())
        return numberFormat.format(number)
    }
}