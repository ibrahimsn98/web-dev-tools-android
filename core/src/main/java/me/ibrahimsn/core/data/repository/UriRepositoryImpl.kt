package me.ibrahimsn.core.data.repository

import android.net.Uri
import me.ibrahimsn.core.data.model.DataHolder
import me.ibrahimsn.core.data.model.ErrorHolder
import me.ibrahimsn.core.domain.model.param.Param
import me.ibrahimsn.core.domain.repository.UriRepository

class UriRepositoryImpl : UriRepository {

    override suspend fun parseUriQueryParams(uri: String): DataHolder<List<Param>?> {
        val parsed = Uri.parse(uri)
        val params = mutableListOf<Param>()

        if (parsed.isOpaque) {
            return DataHolder.Error(ErrorHolder.InvalidUriError)
        }

        for (param in parsed.queryParameterNames) {
            parsed.getQueryParameter(param)?.let {
                params.add(Param(true, param, it))
            }
        }

        return DataHolder.Success(params)
    }

    override suspend fun generateUri(uri: String, queryParams: List<Param>): DataHolder<Uri?> {
        val builder = Uri.parse(uri)
            .buildUpon()
            .clearQuery()

        for (param in queryParams.filter {
            it.isActive && it.key.isNotEmpty() && it.value.isNotEmpty()
        }) {
            builder.appendQueryParameter(param.key, param.value)
        }

        return DataHolder.Success(builder.build())
    }
}
