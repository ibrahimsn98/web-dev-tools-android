package me.ibrahimsn.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.ibrahimsn.core.data.dao.RequestDao
import me.ibrahimsn.core.data.dao.WebsocketDao
import me.ibrahimsn.core.domain.model.request.Request
import me.ibrahimsn.core.domain.model.websocket.Websocket

@Database(entities = [Request::class, Websocket::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun httpRequestDao(): RequestDao
    abstract fun webSocketDao(): WebsocketDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            val tempInstance = INSTANCE

            if (tempInstance != null)
                return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(context, AppDatabase::class.java, "wdevtools").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
