package me.ibrahimsn.request.presentation.request

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import me.ibrahimsn.core.data.model.DataHolder
import me.ibrahimsn.core.domain.interactor.Interactor
import me.ibrahimsn.core.domain.interactor.request.MakeRequestInteractor
import me.ibrahimsn.core.domain.interactor.request.SaveOrUpdateRequestInteractor
import me.ibrahimsn.core.domain.interactor.uri.GenerateUriInteractor
import me.ibrahimsn.core.domain.interactor.uri.ParseUriParamsInteractor
import me.ibrahimsn.core.domain.model.param.Param
import me.ibrahimsn.core.domain.model.request.Request
import me.ibrahimsn.core.domain.model.request.RequestResponse
import me.ibrahimsn.core.presentation.base.BaseViewModel
import me.ibrahimsn.core.presentation.extension.io
import me.ibrahimsn.core.presentation.livedata.SingleLiveEvent

class RequestViewModel (
    private val makeRequestInteractor: Interactor.RequestInteractor<MakeRequestInteractor.Params, RequestResponse?>,
    private val saveOrUpdateRequestInteractor: Interactor.RequestInteractor<SaveOrUpdateRequestInteractor.Params, Long?>,
    private val generateUriInteractor: Interactor.RequestInteractor<GenerateUriInteractor.Params, Uri?>,
    private val parseUriParamsInteractor: Interactor.RequestInteractor<ParseUriParamsInteractor.Params, List<Param>?>
) : BaseViewModel() {

    private val _requestIdLiveData = MutableLiveData<DataHolder<Long?>>()
    val requestIdLiveData: LiveData<DataHolder<Long?>> get() = _requestIdLiveData

    private val _uriLiveData = MutableLiveData<DataHolder<Uri?>>()
    val uriLiveData: LiveData<DataHolder<Uri?>> get() = _uriLiveData

    private val _queryParamsLiveData  = MutableLiveData<DataHolder<List<Param>?>>()
    val queryParamsLiveData: LiveData<DataHolder<List<Param>?>> get() = _queryParamsLiveData

    private val _responseLiveData = SingleLiveEvent<DataHolder<RequestResponse?>>()
    val responseLiveData: LiveData<DataHolder<RequestResponse?>> get() = _responseLiveData

    private var _request: Request? = null
    var request: Request? get() = _request
        set(value) { _request = value }

    fun executeRequest(headerParams: List<Param>?, bodyParams: List<Param>?) {
        viewModelScope.io {
            _responseLiveData.postValue(
                DataHolder.Loading
            )
            _responseLiveData.postValue(
                makeRequestInteractor.invoke(
                    MakeRequestInteractor.Params(
                        uri = request?.uri,
                        method = request?.method,
                        headerParams = headerParams,
                        bodyParams = bodyParams
                    )
                )
            )
        }
    }

    fun parseUriParams(uri: String?) {
        viewModelScope.io {
            _queryParamsLiveData.postValue(
                parseUriParamsInteractor.invoke(
                    ParseUriParamsInteractor.Params(uri)
                )
            )
        }
    }

    fun generateUri(uri: String, queryParams: List<Param>) {
        viewModelScope.io {
            _uriLiveData.postValue(
                generateUriInteractor.invoke(
                    GenerateUriInteractor.Params(
                        uri, queryParams
                    )
                )
            )
        }
    }

    fun updateRequest() {
        viewModelScope.io {
            _requestIdLiveData.postValue(
                saveOrUpdateRequestInteractor.invoke(
                    SaveOrUpdateRequestInteractor.Params(request)
                )
            )
        }
    }
}
