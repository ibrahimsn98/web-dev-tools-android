package me.ibrahimsn.websocket.presentation.websocket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import me.ibrahimsn.core.data.model.DataHolder
import me.ibrahimsn.core.data.model.ErrorHolder
import me.ibrahimsn.core.domain.interactor.Interactor
import me.ibrahimsn.core.domain.interactor.websocket.ConnectWebsocketInteractor
import me.ibrahimsn.core.domain.interactor.websocket.SaveOrUpdateWebsocketInteractor
import me.ibrahimsn.core.domain.model.param.Param
import me.ibrahimsn.core.domain.model.websocket.Websocket
import me.ibrahimsn.core.domain.model.websocket.WebsocketEvent
import me.ibrahimsn.core.presentation.base.BaseViewModel
import me.ibrahimsn.core.presentation.extension.io
import okhttp3.WebSocket

class WebsocketViewModel (
    private val connectWebsocketInteractor: Interactor.ObserveInteractor<ConnectWebsocketInteractor.Params, WebsocketEvent?>,
    private val saveOrUpdateWebsocketInteractor: Interactor.RequestInteractor<SaveOrUpdateWebsocketInteractor.Params, Long?>
) : BaseViewModel() {

    private val _websocketIdLiveData = MutableLiveData<DataHolder<Long?>>()
    val websocketIdLiveData: LiveData<DataHolder<Long?>> get() = _websocketIdLiveData

    private val _connectionStatusLiveData = MutableLiveData<DataHolder<Unit?>>()
    val connectionStatusLiveData: LiveData<DataHolder<Unit?>> get() = _connectionStatusLiveData

    private val _responseLiveData = MutableLiveData<DataHolder<String?>>()
    val responseLiveData: LiveData<DataHolder<String?>> get() = _responseLiveData

    private var _websocket: Websocket? = null
    var websocket: Websocket? get() = _websocket
        set(value) { _websocket = value }

    private var channel = Channel<WebsocketEvent?>()
    private var connection: WebSocket? = null

    init {
        viewModelScope.io {
            for (event in channel) {
                when (event) {
                    is WebsocketEvent.OnOpen -> {
                        connection = event.websocket
                        _connectionStatusLiveData.postValue(
                            DataHolder.Success(Unit)
                        )
                    }
                    is WebsocketEvent.OnClosed -> {
                        connection = null
                        _connectionStatusLiveData.postValue(
                            DataHolder.Error(ErrorHolder.SocketClosedError)
                        )
                    }
                    is WebsocketEvent.OnMessage -> {
                        _responseLiveData.postValue(
                            DataHolder.Success(event.text)
                        )
                    }
                    is WebsocketEvent.OnFailure -> {
                        _connectionStatusLiveData.postValue(
                            DataHolder.Error(event.error)
                        )
                    }
                }
            }
        }
    }

    fun connectWebsocket(headerParams: List<Param>?) {
        viewModelScope.io {
            _connectionStatusLiveData.postValue(
                DataHolder.Loading
            )
            connectWebsocketInteractor.invoke(
                channel,
                ConnectWebsocketInteractor.Params(
                    uri = websocket?.uri,
                    headerParams = headerParams
                )
            )
        }
    }

    fun sendWebSocket(data: String) {
        connection?.send(data)
    }

    fun disconnectWebsocket() {
        connection?.close(1000, null)
        connection?.cancel()
        connection = null
    }

    fun updateWebsocket() {
        viewModelScope.io {
            _websocketIdLiveData.postValue(
                saveOrUpdateWebsocketInteractor.invoke(
                    SaveOrUpdateWebsocketInteractor.Params(websocket)
                )
            )
        }
    }
}
