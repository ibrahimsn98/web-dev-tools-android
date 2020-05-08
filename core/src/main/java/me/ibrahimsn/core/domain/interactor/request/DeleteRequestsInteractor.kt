package me.ibrahimsn.core.domain.interactor.request

import me.ibrahimsn.core.data.model.DataHolder
import me.ibrahimsn.core.data.model.ErrorHolder
import me.ibrahimsn.core.domain.interactor.Interactor
import me.ibrahimsn.core.domain.model.request.Request
import me.ibrahimsn.core.domain.repository.RequestRepository

class DeleteRequestsInteractor (private val repository: RequestRepository)
    : Interactor.RequestInteractor<DeleteRequestsInteractor.Params, Unit?> {

    override suspend fun invoke(params: Params?): DataHolder<Unit?> {

        if (params == null)
            return DataHolder.Error(ErrorHolder.InvalidParamsError)

        if (params.requests == null)
            return DataHolder.Error(ErrorHolder.InvalidParamsError)

        return repository.deleteRequests(params.requests)
    }

    class Params(
        val requests: List<Request>?
    ) : Interactor.Params()

    companion object {
        const val NAME = "DeleteRequestInteractor"
    }
}
