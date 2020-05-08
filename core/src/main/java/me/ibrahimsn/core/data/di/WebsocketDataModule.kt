package me.ibrahimsn.core.data.di

import me.ibrahimsn.core.data.repository.WebsocketRepositoryImpl
import me.ibrahimsn.core.domain.repository.WebsocketRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module

val websocketDataModule = module {

    single {
        provideWebsocketRepository(
            okHttpClient = get(),
            appDatabase = get()
        )
    }
}

private fun provideWebsocketRepository(
    okHttpClient: OkHttpClient,
    appDatabase: me.ibrahimsn.core.data.database.AppDatabase
): WebsocketRepository {
    return WebsocketRepositoryImpl(okHttpClient, appDatabase)
}
