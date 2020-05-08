package me.ibrahimsn.core.domain.di

import me.ibrahimsn.core.domain.interactor.Interactor
import me.ibrahimsn.core.domain.interactor.websocket.ConnectWebsocketInteractor
import me.ibrahimsn.core.domain.interactor.websocket.DeleteWebsocketsInteractor
import me.ibrahimsn.core.domain.interactor.websocket.GetAllWebsocketsInteractor
import me.ibrahimsn.core.domain.interactor.websocket.SaveOrUpdateWebsocketInteractor
import me.ibrahimsn.core.domain.model.websocket.Websocket
import me.ibrahimsn.core.domain.model.websocket.WebsocketEvent
import me.ibrahimsn.core.domain.repository.WebsocketRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val websocketDomainModule = module {

    factory (named(ConnectWebsocketInteractor.NAME)) {
        provideConnectWebsocketInteractor(
            websocketRepository = get()
        )
    }

    factory (named(GetAllWebsocketsInteractor.NAME)) {
        provideGetAllWebsocketsInteractor(
            websocketRepository = get()
        )
    }

    factory (named(SaveOrUpdateWebsocketInteractor.NAME)) {
        provideSaveOrUpdateRequestInteractor(
            websocketRepository = get()
        )
    }

    factory (named(DeleteWebsocketsInteractor.NAME)) {
        provideDeleteRequestsInteractor(
            websocketRepository = get()
        )
    }
}

private fun provideConnectWebsocketInteractor(websocketRepository: WebsocketRepository)
        : Interactor.ObserveInteractor<ConnectWebsocketInteractor.Params, WebsocketEvent?>{
    return ConnectWebsocketInteractor(websocketRepository)
}

private fun provideGetAllWebsocketsInteractor(websocketRepository: WebsocketRepository)
        : Interactor.PagingInteractor<Int, Websocket> {
    return GetAllWebsocketsInteractor(websocketRepository)
}

private fun provideSaveOrUpdateRequestInteractor(websocketRepository: WebsocketRepository)
        : Interactor.RequestInteractor<SaveOrUpdateWebsocketInteractor.Params, Long?> {
    return SaveOrUpdateWebsocketInteractor(websocketRepository)
}

private fun provideDeleteRequestsInteractor(websocketRepository: WebsocketRepository)
        : Interactor.RequestInteractor<DeleteWebsocketsInteractor.Params, Unit?> {
    return DeleteWebsocketsInteractor(websocketRepository)
}
