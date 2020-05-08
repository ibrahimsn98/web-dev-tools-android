package me.ibrahimsn.core.domain.interactor.uri

import android.net.Uri
import me.ibrahimsn.core.data.model.DataHolder
import me.ibrahimsn.core.data.model.ErrorHolder
import me.ibrahimsn.core.domain.interactor.Interactor
import me.ibrahimsn.core.domain.model.param.Param
import me.ibrahimsn.core.domain.repository.UriRepository

class GenerateUriInteractor (private val uriRepository: UriRepository)
    : Interactor.RequestInteractor<GenerateUriInteractor.Params, Uri?> {

    override suspend fun invoke(params: Params?): DataHolder<Uri?> {

        if (params == null)
            return DataHolder.Error(ErrorHolder.InvalidParamsError)

        return uriRepository.generateUri(params.uri, params.queryParams)
    }

    class Params(val uri: String, val queryParams: List<Param>) : Interactor.Params()

    companion object {
        const val NAME = "GenerateUriInteractor"
    }
}
