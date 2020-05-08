package me.ibrahimsn.core.domain.interactor.websocket

import me.ibrahimsn.core.data.model.DataHolder
import me.ibrahimsn.core.data.model.ErrorHolder
import me.ibrahimsn.core.domain.interactor.Interactor
import me.ibrahimsn.core.domain.model.websocket.Websocket
import me.ibrahimsn.core.domain.repository.WebsocketRepository

class SaveOrUpdateWebsocketInteractor (private val repository: WebsocketRepository)
    : Interactor.RequestInteractor<SaveOrUpdateWebsocketInteractor.Params, Long?> {

    override suspend fun invoke(params: Params?): DataHolder<Long?> {

        if (params == null)
            return DataHolder.Error(ErrorHolder.InvalidParamsError)

        if (params.websocket == null)
            return DataHolder.Error(ErrorHolder.InvalidParamsError)

        return repository.saveOrUpdateWebsocket(params.websocket)
    }

    class Params(
        val websocket: Websocket?
    ) : Interactor.Params()

    companion object {
        const val NAME = "UpdateWebsocketInteractor"
    }
}
