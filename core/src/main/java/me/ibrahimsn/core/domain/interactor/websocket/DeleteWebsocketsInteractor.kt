package me.ibrahimsn.core.domain.interactor.websocket

import me.ibrahimsn.core.data.model.DataHolder
import me.ibrahimsn.core.data.model.ErrorHolder
import me.ibrahimsn.core.domain.interactor.Interactor
import me.ibrahimsn.core.domain.model.websocket.Websocket
import me.ibrahimsn.core.domain.repository.WebsocketRepository

class DeleteWebsocketsInteractor (private val repository: WebsocketRepository)
    : Interactor.RequestInteractor<DeleteWebsocketsInteractor.Params, Unit?> {

    override suspend fun invoke(params: Params?): DataHolder<Unit?> {

        if (params == null)
            return DataHolder.Error(ErrorHolder.InvalidParamsError)

        if (params.websockets == null)
            return DataHolder.Error(ErrorHolder.InvalidParamsError)

        return repository.deleteWebsockets(params.websockets)
    }

    class Params(
        val websockets: List<Websocket>?
    ) : Interactor.Params()

    companion object {
        const val NAME = "DeleteWebsocketsInteractor"
    }
}
