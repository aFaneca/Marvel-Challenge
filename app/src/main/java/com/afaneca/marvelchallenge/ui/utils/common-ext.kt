package com.afaneca.marvelchallenge.ui

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

/**
 * If the string representation of an Url doesn't start with "https",
 * replace "http" with "https" (if it's not found, [String] is returned as-is)
 */
fun String.normalizeUrlToHttps(): String {
    return if (startsWith("https")) this
    else replace("http", "https")
}

fun Activity.hideSoftKeyboard() {
    currentFocus?.let {
        val inputMethodManager =
            ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}