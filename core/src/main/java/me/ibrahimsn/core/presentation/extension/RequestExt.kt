package me.ibrahimsn.core.presentation.extension

import com.google.gson.Gson
import me.ibrahimsn.core.domain.model.param.Param
import me.ibrahimsn.core.domain.model.request.Request

fun Request?.getHeaderParams(): List<Param>? {
    this?.headerParams?.let {
        return Gson().fromJson(it, Array<Param>::class.java)?.toList()
    }

    return null
}

fun Request?.getBodyParams(): List<Param>? {
    this?.bodyParams?.let {
        return Gson().fromJson(it, Array<Param>::class.java)?.toList()
    }

    return null
}