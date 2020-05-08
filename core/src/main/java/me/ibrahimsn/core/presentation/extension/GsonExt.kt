package me.ibrahimsn.core.presentation.extension

import com.google.gson.Gson

fun Any?.toJson(): String? {
    return Gson().toJson(this)
}