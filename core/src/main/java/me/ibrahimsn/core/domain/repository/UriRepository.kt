package me.ibrahimsn.core.domain.repository

import android.net.Uri
import me.ibrahimsn.core.data.model.DataHolder
import me.ibrahimsn.core.domain.model.param.Param

interface UriRepository {

    suspend fun parseUriQueryParams(uri: String): DataHolder<List<Param>?>

    suspend fun generateUri(uri: String, queryParams: List<Param>): DataHolder<Uri?>
}
