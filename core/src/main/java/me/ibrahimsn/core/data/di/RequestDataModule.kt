package me.ibrahimsn.core.data.di

import me.ibrahimsn.core.data.database.AppDatabase
import me.ibrahimsn.core.data.repository.RequestRepositoryImpl
import me.ibrahimsn.core.domain.repository.RequestRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module

val requestDataModule = module {
    
    single {
        provideRequestRepository(
            okHttpClient = get(),
            appDatabase = get()
        )
    }
}

private fun provideRequestRepository(
    okHttpClient: OkHttpClient,
    appDatabase: AppDatabase
): RequestRepository {
    return RequestRepositoryImpl(
        okHttpClient,
        appDatabase
    )
}
