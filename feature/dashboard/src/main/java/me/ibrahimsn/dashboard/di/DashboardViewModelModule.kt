package me.ibrahimsn.dashboard.di

import me.ibrahimsn.core.domain.interactor.request.DeleteRequestsInteractor
import me.ibrahimsn.core.domain.interactor.request.GetAllRequestsInteractor
import me.ibrahimsn.core.domain.interactor.websocket.DeleteWebsocketsInteractor
import me.ibrahimsn.core.domain.interactor.websocket.GetAllWebsocketsInteractor
import me.ibrahimsn.dashboard.presentation.dashboard.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dashboardViewModelModule = module {
    
    viewModel {
        DashboardViewModel(
            getAllRequestsInteractor = get(named(GetAllRequestsInteractor.NAME)),
            getAllWebsocketsInteractor = get(named(GetAllWebsocketsInteractor.NAME)),
            deleteRequestsInteractor = get(named(DeleteRequestsInteractor.NAME)),
            deleteWebsocketsInteractor = get(named(DeleteWebsocketsInteractor.NAME))
        )
    }
}
