package me.ibrahimsn.core.domain.interactor.request

import me.ibrahimsn.core.data.model.DataHolder
import me.ibrahimsn.core.data.model.ErrorHolder
import me.ibrahimsn.core.domain.interactor.Interactor
import me.ibrahimsn.core.domain.model.request.Request
import me.ibrahimsn.core.domain.repository.RequestRepository

class SaveOrUpdateRequestInteractor (private val repository: RequestRepository)
    : Interactor.RequestInteractor<SaveOrUpdateRequestInteractor.Params, Long?> {

    override suspend fun invoke(params: Params?): DataHolder<Long?> {

        if (params == null)
            return DataHolder.Error(ErrorHolder.InvalidParamsError)

        if (params.request == null)
            return DataHolder.Error(ErrorHolder.InvalidParamsError)

        return repository.saveOrUpdateRequest(params.request)
    }

    class Params(
        val request: Request?
    ) : Interactor.Params()

    companion object {
        const val NAME = "UpdateRequestInteractor"
    }
}
