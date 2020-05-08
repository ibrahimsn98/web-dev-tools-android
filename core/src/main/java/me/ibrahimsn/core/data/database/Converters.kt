package me.ibrahimsn.core.data.database

import androidx.room.TypeConverter
import me.ibrahimsn.core.data.model.RequestMethod
import java.util.*

class Converters {

    @TypeConverter
    fun toRequestMethod(value: Int) = enumValues<RequestMethod>()[value]

    @TypeConverter
    fun fromRequestMethod(value: RequestMethod) = value.ordinal

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
