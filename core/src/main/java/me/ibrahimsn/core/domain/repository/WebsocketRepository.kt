package me.ibrahimsn.core.domain.repository

import androidx.paging.DataSource
import kotlinx.coroutines.channels.Channel
import me.ibrahimsn.core.data.model.DataHolder
import me.ibrahimsn.core.domain.model.param.Param
import me.ibrahimsn.core.domain.model.websocket.Websocket
import me.ibrahimsn.core.domain.model.websocket.WebsocketEvent

interface WebsocketRepository {

    suspend fun connectWebsocket(
        channel: Channel<WebsocketEvent?>,
        uri: String,
        headerParams: List<Param>?
    )

    fun getAllWebsockets(): DataSource.Factory<Int, Websocket>

    suspend fun saveOrUpdateWebsocket(websocket: Websocket): DataHolder<Long?>

    suspend fun deleteWebsockets(websocket: List<Websocket>): DataHolder<Unit?>
}
