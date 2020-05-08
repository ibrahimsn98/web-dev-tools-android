package me.ibrahimsn.core.data.di

import me.ibrahimsn.core.domain.source.LocalDataSource
import okhttp3.OkHttpClient
import org.koin.dsl.module

val httpModule = module {

    single {
        provideOkHttpClient(preferences = get())
    }
}

private fun provideOkHttpClient(preferences: LocalDataSource.Preferences): OkHttpClient {
    return OkHttpClient
        .Builder()
        .followRedirects(preferences.isFollowRedirect())
        .followSslRedirects(preferences.isFollowRedirect())
        .build()
}
