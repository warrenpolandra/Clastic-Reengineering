package com.clastic.utils

import android.util.Patterns
import java.net.MalformedURLException
import java.net.URL

object TextUtils {
    fun isValidURL(url: String): Boolean {
        val urlPattern = "^(http://|https://).*$".toRegex()
        return if (urlPattern.matches(url) && Patterns.WEB_URL.matcher(url).matches()) {
            try {
                URL(url)
                true
            } catch (e: MalformedURLException) { false }
        } else { false }
    }
}