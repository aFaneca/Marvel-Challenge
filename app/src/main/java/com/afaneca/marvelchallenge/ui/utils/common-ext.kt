package com.afaneca.marvelchallenge.ui

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDirections

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

/**
 * Avoids [IllegalArgumentException] on quick double tap by only trying to perform the navigation
 * if the destination is known by the current destination
 */
fun NavController.safeNavigate(navDirections: NavDirections) {
    currentDestination?.getAction(navDirections.actionId)?.let { navigate(navDirections) }
}