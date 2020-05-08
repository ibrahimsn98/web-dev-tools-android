package me.ibrahimsn.core.presentation.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date?.toDateString(): String? {
    this?.let {
        return SimpleDateFormat("dd MMM hh:mm", Locale.getDefault()).format(this)
    }
    return null
}