package me.ibrahimsn.core.domain.interactor.websocket

import androidx.paging.DataSource
import me.ibrahimsn.core.domain.interactor.Interactor
import me.ibrahimsn.core.domain.model.websocket.Websocket
import me.ibrahimsn.core.domain.repository.WebsocketRepository

class GetAllWebsocketsInteractor (private val repository: WebsocketRepository)
    : Interactor.PagingInteractor<Int, Websocket> {

    override fun invoke(): DataSource.Factory<Int, Websocket> {
        return repository.getAllWebsockets()
    }

    companion object {
        const val NAME = "GetAllWebsocketsInteractor"
    }
}
