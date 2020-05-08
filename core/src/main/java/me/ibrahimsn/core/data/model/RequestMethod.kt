package me.ibrahimsn.core.data.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import me.ibrahimsn.core.R

enum class RequestMethod(
    val hasBody: Boolean,
    @ColorRes val colorRes: Int,
    @DrawableRes val backgroundRes: Int,
    val shortName: String
) {

    GET(
        false,
        R.color.colorGet,
        R.drawable.ic_background_method_get,
        "GET"
    ),

    POST(
        true,
        R.color.colorPost,
        R.drawable.ic_background_method_post,
        "PST"
    ),

    PUT(
        true,
        R.color.colorPut,
        R.drawable.ic_background_method_put,
        "PUT"
    ),

    DEL(
        true,
        R.color.colorDelete,
        R.drawable.ic_background_method_delete,
        "DEL"
    ),

    PATCH(
        true,
        R.color.colorPatch,
        R.drawable.ic_background_method_patch,
        "PCH"
    )
}
