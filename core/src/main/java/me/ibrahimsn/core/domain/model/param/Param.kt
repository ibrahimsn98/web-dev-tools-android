package me.ibrahimsn.core.domain.model.param

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Param(
    var isActive: Boolean,
    var key: String,
    var value: String
): Parcelable {

    companion object {

        fun newInstance(): Param {
            return Param(true, "", "")
        }
    }
}
