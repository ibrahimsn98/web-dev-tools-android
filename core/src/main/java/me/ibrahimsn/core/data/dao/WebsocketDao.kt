package me.ibrahimsn.core.data.dao

import androidx.paging.DataSource
import androidx.room.*
import me.ibrahimsn.core.domain.model.websocket.Websocket

@Dao
abstract class WebsocketDao {

    @Query("SELECT * from websockets")
    abstract fun getAll(): DataSource.Factory<Int, Websocket>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertAsync(websocket: Websocket): Long

    @Update
    abstract suspend fun update(websocket: Websocket)

    @Delete
    abstract suspend fun delete(websocket: Websocket)

    @Transaction
    open suspend fun insertOrUpdate(websocket: Websocket): Long? {
        val insert = insertAsync(websocket)
        if (insert != -1L) return insert

        update(websocket)
        return websocket.id
    }

    @Transaction
    open suspend fun delete(websockets: List<Websocket>) {
        for (websocket in websockets) {
            delete(websocket)
        }
    }
}
