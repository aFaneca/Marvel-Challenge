package com.afaneca.marvelchallenge.ui

/**
 * If the string representation of an Url doesn't start with "https",
 * replace "http" with "https" (if it's not found, [String] is returned as-is)
 */
fun String.normalizeUrlToHttps(): String {
    return if (startsWith("https")) this
    else replace("http", "https")
}