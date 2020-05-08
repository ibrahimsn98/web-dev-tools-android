package me.ibrahimsn.core.presentation.extension

import com.google.gson.Gson
import me.ibrahimsn.core.domain.model.param.Param
import me.ibrahimsn.core.domain.model.websocket.Websocket

fun Websocket?.getHeaderParams(): List<Param>? {
    this?.headerParams?.let {
        return Gson().fromJson(it, Array<Param>::class.java)?.toList()
    }

    return null
}
