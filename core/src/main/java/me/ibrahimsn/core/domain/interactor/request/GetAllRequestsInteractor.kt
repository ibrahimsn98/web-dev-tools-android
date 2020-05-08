package me.ibrahimsn.core.domain.interactor.request

import androidx.paging.DataSource
import me.ibrahimsn.core.domain.interactor.Interactor
import me.ibrahimsn.core.domain.model.request.Request
import me.ibrahimsn.core.domain.repository.RequestRepository

class GetAllRequestsInteractor (private val repository: RequestRepository)
    : Interactor.PagingInteractor<Int, Request> {

    override fun invoke(): DataSource.Factory<Int, Request> {
        return repository.getAllRequests()
    }

    companion object {
        const val NAME = "GetAllRequestsInteractor"
    }
}
