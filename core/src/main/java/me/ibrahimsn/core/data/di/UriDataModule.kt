package me.ibrahimsn.core.data.di

import me.ibrahimsn.core.data.repository.UriRepositoryImpl
import me.ibrahimsn.core.domain.repository.UriRepository
import org.koin.dsl.module

val uriDataModule = module {

    single {
        provideUriRepository()
    }
}

private fun provideUriRepository(): UriRepository {
    return UriRepositoryImpl()
}
