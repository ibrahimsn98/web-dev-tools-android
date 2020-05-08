package me.ibrahimsn.wdevtools.di

import me.ibrahimsn.dashboard.di.dashboardViewModelModule
import me.ibrahimsn.request.di.requestViewModelModule
import me.ibrahimsn.websocket.di.websocketViewModelModule

val appViewModelModule = listOf(
    dashboardViewModelModule,
    requestViewModelModule,
    websocketViewModelModule
)
