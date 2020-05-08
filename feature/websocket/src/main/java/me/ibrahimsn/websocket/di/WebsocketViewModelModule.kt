package me.ibrahimsn.websocket.di

import me.ibrahimsn.core.domain.interactor.websocket.ConnectWebsocketInteractor
import me.ibrahimsn.core.domain.interactor.websocket.SaveOrUpdateWebsocketInteractor
import me.ibrahimsn.websocket.presentation.websocket.WebsocketViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val websocketViewModelModule = module {

    viewModel {
        WebsocketViewModel(
            connectWebsocketInteractor = get(named(ConnectWebsocketInteractor.NAME)),
            saveOrUpdateWebsocketInteractor = get(named(SaveOrUpdateWebsocketInteractor.NAME))
        )
    }
}
