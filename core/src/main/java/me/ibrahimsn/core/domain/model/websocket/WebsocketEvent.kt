package me.ibrahimsn.core.domain.model.websocket

import me.ibrahimsn.core.data.model.ErrorHolder
import okhttp3.WebSocket

sealed class WebsocketEvent {

    object OnClosed: WebsocketEvent()
    data class OnOpen(val websocket: WebSocket): WebsocketEvent()
    data class OnMessage(val text: String): WebsocketEvent()
    data class OnFailure(val error: ErrorHolder): WebsocketEvent()
}