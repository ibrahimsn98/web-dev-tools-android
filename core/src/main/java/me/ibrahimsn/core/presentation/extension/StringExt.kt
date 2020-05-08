package me.ibrahimsn.core.presentation.extension

import okhttp3.Request

fun String?.isValidUri(): Boolean {
    if (this.isNullOrEmpty()) return false
    return try {
        Request.Builder().url(this)
        true
    } catch (e: Exception) {
        false
    }
}