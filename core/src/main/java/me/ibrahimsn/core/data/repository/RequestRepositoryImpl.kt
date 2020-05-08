package me.ibrahimsn.core.data.repository

import androidx.paging.DataSource
import me.ibrahimsn.core.data.database.AppDatabase
import me.ibrahimsn.core.data.model.DataHolder
import me.ibrahimsn.core.data.model.ErrorHolder
import me.ibrahimsn.core.data.model.RequestMethod
import me.ibrahimsn.core.domain.model.param.Param
import me.ibrahimsn.core.domain.model.request.Request
import me.ibrahimsn.core.domain.model.request.RequestResponse
import me.ibrahimsn.core.domain.repository.RequestRepository
import okhttp3.*
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RequestRepositoryImpl (
    private val okHttpClient: OkHttpClient,
    private val appDatabase: AppDatabase
) : RequestRepository {

    override suspend fun makeRequest(
        uri: String,
        method: RequestMethod,
        headerParams: List<Param>?,
        bodyParams: List<Param>?
    ): DataHolder<RequestResponse?> {

        val requestBuilder = okhttp3.Request.Builder()
            .url(uri)
        var requestHeaders: Headers? = null
        var requestBody: RequestBody? = null

        headerParams?.filter {
            it.isActive && it.key.isNotEmpty() && it.value.isNotEmpty()
        }?.let { params ->
            if (params.isNotEmpty()) {
                requestHeaders = Headers.Builder().apply {
                    params.forEach { param ->
                        add(param.key, param.value)
                    }
                }.build()
            }
        }

        requestHeaders?.let {
            requestBuilder.headers(it)
        }

        bodyParams?.filter {
            it.isActive && it.key.isNotEmpty() && it.value.isNotEmpty()
        }?.let { params ->
            if (params.isNotEmpty()) {
                requestBody = MultipartBody.Builder().setType(MultipartBody.FORM).apply {
                    params.forEach { param ->
                        addFormDataPart(param.key, param.value)
                    }
                }.build()
            }
        }

        requestBuilder.method(method.name, requestBody)

        return suspendCoroutine { continuation ->
            okHttpClient.newCall(requestBuilder.build()).enqueue(object: Callback {
                override fun onResponse(call: Call, response: Response) {
                    continuation.resume(
                        DataHolder.Success(
                            RequestResponse(
                                status = response.isSuccessful,
                                code = response.code,
                                body = response.body?.string()
                            )
                        )
                    )
                }

                override fun onFailure(call: Call, e: IOException) {
                    continuation.resume(
                        DataHolder.Error(ErrorHolder.NetworkError(e))
                    )
                }
            })
        }
    }

    override fun getAllRequests(): DataSource.Factory<Int, Request> {
        return appDatabase.httpRequestDao().getAll()
    }

    override suspend fun saveOrUpdateRequest(request: Request): DataHolder<Long?> {
        return DataHolder.Success(appDatabase.httpRequestDao().insertOrUpdate(request))
    }

    override suspend fun deleteRequests(requests: List<Request>): DataHolder<Unit?> {
        return DataHolder.Success(appDatabase.httpRequestDao().delete(requests))
    }
}
