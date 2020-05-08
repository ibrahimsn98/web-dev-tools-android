package me.ibrahimsn.request.di

import me.ibrahimsn.core.domain.interactor.request.MakeRequestInteractor
import me.ibrahimsn.core.domain.interactor.request.SaveOrUpdateRequestInteractor
import me.ibrahimsn.core.domain.interactor.uri.GenerateUriInteractor
import me.ibrahimsn.core.domain.interactor.uri.ParseUriParamsInteractor
import me.ibrahimsn.request.presentation.request.RequestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val requestViewModelModule = module {

    viewModel {
        RequestViewModel(
            makeRequestInteractor = get(named(MakeRequestInteractor.NAME)),
            saveOrUpdateRequestInteractor = get(named(SaveOrUpdateRequestInteractor.NAME)),
            generateUriInteractor = get(named(GenerateUriInteractor.NAME)),
            parseUriParamsInteractor = get(named(ParseUriParamsInteractor.NAME))
        )
    }
}
