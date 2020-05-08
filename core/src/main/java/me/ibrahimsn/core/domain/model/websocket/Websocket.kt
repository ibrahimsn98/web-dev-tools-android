package me.ibrahimsn.core.domain.model.websocket

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "websockets")
data class Websocket(
    var uri: String,
    var headerParams: String,
    var createdAt: Date,
    @PrimaryKey(autoGenerate = true) var id: Long? = null
): Parcelable {

    companion object {

        fun newInstance(uri: String): Websocket {
            return Websocket(uri, "", Date())
        }
    }
}