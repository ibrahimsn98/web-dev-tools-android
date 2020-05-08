package me.ibrahimsn.wdevtools.di

import me.ibrahimsn.core.data.di.*

val appDataModule = listOf(
    dataModule,
    httpModule,
    requestDataModule,
    websocketDataModule,
    uriDataModule
)
