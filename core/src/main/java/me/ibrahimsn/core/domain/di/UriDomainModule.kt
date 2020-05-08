package me.ibrahimsn.core.domain.di

import android.net.Uri
import me.ibrahimsn.core.domain.interactor.Interactor
import me.ibrahimsn.core.domain.interactor.uri.GenerateUriInteractor
import me.ibrahimsn.core.domain.interactor.uri.ParseUriParamsInteractor
import me.ibrahimsn.core.domain.model.param.Param
import me.ibrahimsn.core.domain.repository.UriRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val uriDomainModule = module {

    factory(named(GenerateUriInteractor.NAME)) {
        provideGenerateUriInteractor(uriRepository = get())
    }

    factory(named(ParseUriParamsInteractor.NAME)) {
        provideParseUriParamsInteractor(uriRepository = get())
    }
}

private fun provideGenerateUriInteractor(uriRepository: UriRepository)
        : Interactor.RequestInteractor<GenerateUriInteractor.Params, Uri?> {
    return GenerateUriInteractor(uriRepository)
}

private fun provideParseUriParamsInteractor(uriRepository: UriRepository)
        : Interactor.RequestInteractor<ParseUriParamsInteractor.Params, List<Param>?> {
    return ParseUriParamsInteractor(uriRepository)
}
