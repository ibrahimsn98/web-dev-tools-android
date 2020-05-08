package me.ibrahimsn.core.domain.interactor.request

import me.ibrahimsn.core.data.model.DataHolder
import me.ibrahimsn.core.data.model.ErrorHolder
import me.ibrahimsn.core.data.model.RequestMethod
import me.ibrahimsn.core.domain.interactor.Interactor
import me.ibrahimsn.core.domain.model.param.Param
import me.ibrahimsn.core.domain.model.request.RequestResponse
import me.ibrahimsn.core.domain.repository.RequestRepository
import me.ibrahimsn.core.presentation.extension.isValidUri

class MakeRequestInteractor (private val repository: RequestRepository)
    : Interactor.RequestInteractor<MakeRequestInteractor.Params, RequestResponse?> {

    override suspend fun invoke(params: Params?): DataHolder<RequestResponse?> {

        if (params == null)
            return DataHolder.Error(ErrorHolder.InvalidParamsError)

        if (params.uri.isNullOrEmpty())
            return DataHolder.Error(ErrorHolder.EmptyUriError)

        if (!params.uri.isValidUri())
            return DataHolder.Error(ErrorHolder.InvalidUriError)

        if (params.method == null)
            return DataHolder.Error(ErrorHolder.InvalidParamsError)

        if (params.method.hasBody && params.bodyParams.isNullOrEmpty())
            return DataHolder.Error(ErrorHolder.EmptyBodyError)

        return repository.makeRequest(
            uri = params.uri,
            method = params.method,
            headerParams = params.headerParams,
            bodyParams = params.bodyParams
        )
    }

    class Params(
        val uri: String?,
        val method: RequestMethod?,
        val headerParams: List<Param>?,
        val bodyParams: List<Param>?
    ) : Interactor.Params()

    companion object {
        const val NAME = "MakeRequestInteractor"
    }
}
