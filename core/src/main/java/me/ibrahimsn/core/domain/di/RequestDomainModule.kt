package me.ibrahimsn.core.domain.di

import me.ibrahimsn.core.domain.interactor.Interactor
import me.ibrahimsn.core.domain.interactor.request.DeleteRequestsInteractor
import me.ibrahimsn.core.domain.interactor.request.GetAllRequestsInteractor
import me.ibrahimsn.core.domain.interactor.request.MakeRequestInteractor
import me.ibrahimsn.core.domain.interactor.request.SaveOrUpdateRequestInteractor
import me.ibrahimsn.core.domain.model.request.Request
import me.ibrahimsn.core.domain.model.request.RequestResponse
import me.ibrahimsn.core.domain.repository.RequestRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val requestDomainModule = module {

    factory (named(GetAllRequestsInteractor.NAME)) {
        provideGetAllRequestsInteractor(
            requestRepository = get()
        )
    }

    factory (named(MakeRequestInteractor.NAME)) {
        provideMakeRequestInteractor(
            requestRepository = get()
        )
    }

    factory (named(SaveOrUpdateRequestInteractor.NAME)) {
        provideSaveOrUpdateRequestInteractor(
            requestRepository = get()
        )
    }

    factory (named(DeleteRequestsInteractor.NAME)) {
        provideDeleteRequestsInteractor(
            requestRepository = get()
        )
    }
}

private fun provideGetAllRequestsInteractor(requestRepository: RequestRepository)
        : Interactor.PagingInteractor<Int, Request> {
    return GetAllRequestsInteractor(requestRepository)
}

private fun provideMakeRequestInteractor(requestRepository: RequestRepository)
        : Interactor.RequestInteractor<MakeRequestInteractor.Params, RequestResponse?> {
    return MakeRequestInteractor(requestRepository)
}

private fun provideSaveOrUpdateRequestInteractor(requestRepository: RequestRepository)
        : Interactor.RequestInteractor<SaveOrUpdateRequestInteractor.Params, Long?> {
    return SaveOrUpdateRequestInteractor(requestRepository)
}

private fun provideDeleteRequestsInteractor(requestRepository: RequestRepository)
        : Interactor.RequestInteractor<DeleteRequestsInteractor.Params, Unit?> {
    return DeleteRequestsInteractor(requestRepository)
}
