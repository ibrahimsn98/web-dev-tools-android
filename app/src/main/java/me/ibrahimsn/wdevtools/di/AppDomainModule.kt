package me.ibrahimsn.wdevtools.di

import me.ibrahimsn.core.domain.di.requestDomainModule
import me.ibrahimsn.core.domain.di.uriDomainModule
import me.ibrahimsn.core.domain.di.websocketDomainModule

val appDomainModule = listOf(
    requestDomainModule,
    websocketDomainModule,
    uriDomainModule
)
