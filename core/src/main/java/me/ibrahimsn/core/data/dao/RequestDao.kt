package me.ibrahimsn.core.data.dao

import androidx.paging.DataSource
import androidx.room.*
import me.ibrahimsn.core.domain.model.request.Request

@Dao
abstract class RequestDao {

    @Query("SELECT * from requests")
    abstract fun getAll(): DataSource.Factory<Int, Request>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertAsync(request: Request): Long

    @Update
    abstract suspend fun update(request: Request)

    @Delete
    abstract suspend fun delete(request: Request)

    @Transaction
    open suspend fun insertOrUpdate(request: Request): Long? {
        val insert = insertAsync(request)
        if (insert != -1L) return insert

        update(request)
        return request.id
    }

    @Transaction
    open suspend fun delete(requests: List<Request>) {
        for (request in requests) {
            delete(request)
        }
    }
}
