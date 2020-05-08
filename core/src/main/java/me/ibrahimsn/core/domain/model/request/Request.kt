package me.ibrahimsn.core.domain.model.request

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import me.ibrahimsn.core.data.model.RequestMethod
import java.util.*

@Parcelize
@Entity(tableName = "requests")
data class Request(
    var uri: String,
    var headerParams: String,
    var bodyParams: String,
    var method: RequestMethod,
    var createdAt: Date,
    @PrimaryKey(autoGenerate = true) var id: Long? = null
): Parcelable {

    companion object {

        fun newInstance(uri: String): Request {
            return Request(uri, "", "", RequestMethod.GET, Date())
        }
    }
}
