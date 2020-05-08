package me.ibrahimsn.core.domain.repository

import androidx.paging.DataSource
import me.ibrahimsn.core.data.model.DataHolder
import me.ibrahimsn.core.data.model.RequestMethod
import me.ibrahimsn.core.domain.model.param.Param
import me.ibrahimsn.core.domain.model.request.Request
import me.ibrahimsn.core.domain.model.request.RequestResponse

interface RequestRepository {

    suspend fun makeRequest(
        uri: String,
        method: RequestMethod,
        headerParams: List<Param>?,
        bodyParams: List<Param>?
    ): DataHolder<RequestResponse?>

    fun getAllRequests(): DataSource.Factory<Int, Request>

    suspend fun saveOrUpdateRequest(request: Request): DataHolder<Long?>

    suspend fun deleteRequests(requests: List<Request>): DataHolder<Unit?>
}
