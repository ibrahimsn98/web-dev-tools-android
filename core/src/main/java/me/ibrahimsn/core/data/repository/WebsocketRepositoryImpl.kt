package me.ibrahimsn.core.data.repository

import androidx.paging.DataSource
import kotlinx.coroutines.channels.Channel
import me.ibrahimsn.core.data.database.AppDatabase
import me.ibrahimsn.core.data.model.DataHolder
import me.ibrahimsn.core.data.model.ErrorHolder
import me.ibrahimsn.core.domain.model.param.Param
import me.ibrahimsn.core.domain.model.websocket.Websocket
import me.ibrahimsn.core.domain.model.websocket.WebsocketEvent
import me.ibrahimsn.core.domain.repository.WebsocketRepository
import okhttp3.*

class WebsocketRepositoryImpl (
    private val okHttpClient: OkHttpClient,
    private val appDatabase: AppDatabase
) : WebsocketRepository {

    override suspend fun connectWebsocket(
        channel: Channel<WebsocketEvent?>,
        uri: String,
        headerParams: List<Param>?
    ) {
        val requestBuilder = Request.Builder().url(uri)
        var requestHeaders: Headers? = null

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

        okHttpClient.newWebSocket(requestBuilder.build(), object: WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                channel.offer(WebsocketEvent.OnOpen(webSocket))
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                channel.offer(WebsocketEvent.OnClosed)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                channel.offer(WebsocketEvent.OnMessage(text))
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                channel.offer(
                    WebsocketEvent.OnFailure(
                        ErrorHolder.SocketError(t)
                    )
                )
            }
        })
    }

    override fun getAllWebsockets(): DataSource.Factory<Int, Websocket> {
        return appDatabase.webSocketDao().getAll()
    }

    override suspend fun saveOrUpdateWebsocket(websocket: Websocket): DataHolder<Long?> {
        return DataHolder.Success(appDatabase.webSocketDao().insertOrUpdate(websocket))
    }

    override suspend fun deleteWebsockets(websocket: List<Websocket>): DataHolder<Unit?> {
        return DataHolder.Success(appDatabase.webSocketDao().delete(websocket))
    }
}
