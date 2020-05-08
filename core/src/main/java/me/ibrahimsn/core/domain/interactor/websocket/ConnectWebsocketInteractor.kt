package me.ibrahimsn.core.domain.interactor.websocket

import kotlinx.coroutines.channels.Channel
import me.ibrahimsn.core.data.model.ErrorHolder
import me.ibrahimsn.core.domain.interactor.Interactor
import me.ibrahimsn.core.domain.model.param.Param
import me.ibrahimsn.core.domain.model.websocket.WebsocketEvent
import me.ibrahimsn.core.domain.repository.WebsocketRepository
import me.ibrahimsn.core.presentation.extension.isValidUri

class ConnectWebsocketInteractor (private val repository: WebsocketRepository)
    : Interactor.ObserveInteractor<ConnectWebsocketInteractor.Params, WebsocketEvent?> {

    override suspend fun invoke(channel: Channel<WebsocketEvent?>, params: Params?) {

        if (params == null) {
            channel.offer(WebsocketEvent.OnFailure(ErrorHolder.InvalidParamsError))
            return
        }

        if (params.uri.isNullOrEmpty()) {
            channel.offer(WebsocketEvent.OnFailure(ErrorHolder.EmptyUriError))
            return
        }

        if (!params.uri.isValidUri()) {
            channel.offer(WebsocketEvent.OnFailure(ErrorHolder.InvalidUriError))
            return
        }

        repository.connectWebsocket(
            channel = channel,
            uri = params.uri,
            headerParams = params.headerParams
        )
    }

    class Params(
        val uri: String?,
        val headerParams: List<Param>?
    ) : Interactor.Params()

    companion object {
        const val NAME = "ConnectWebsocketInteractor"
    }
}
