package me.ibrahimsn.core.domain.interactor.uri

import me.ibrahimsn.core.data.model.DataHolder
import me.ibrahimsn.core.data.model.ErrorHolder
import me.ibrahimsn.core.domain.interactor.Interactor
import me.ibrahimsn.core.domain.model.param.Param
import me.ibrahimsn.core.domain.repository.UriRepository

class ParseUriParamsInteractor (private val uriRepository: UriRepository)
    : Interactor.RequestInteractor<ParseUriParamsInteractor.Params, List<Param>?> {

    override suspend fun invoke(params: Params?): DataHolder<List<Param>?> {

        if (params == null)
            return DataHolder.Error(ErrorHolder.InvalidParamsError)

        if (params.uri == null)
            return DataHolder.Error(ErrorHolder.EmptyUriError)

        return uriRepository.parseUriQueryParams(params.uri)
    }

    class Params(val uri: String?) : Interactor.Params()

    companion object {
        const val NAME = "ParseUriParamsInteractor"
    }
}
