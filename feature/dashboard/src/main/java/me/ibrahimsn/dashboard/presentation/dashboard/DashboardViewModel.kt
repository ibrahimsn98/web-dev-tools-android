package me.ibrahimsn.dashboard.presentation.dashboard

import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import me.ibrahimsn.core.domain.interactor.Interactor
import me.ibrahimsn.core.domain.interactor.request.DeleteRequestsInteractor
import me.ibrahimsn.core.domain.interactor.websocket.DeleteWebsocketsInteractor
import me.ibrahimsn.core.domain.model.request.Request
import me.ibrahimsn.core.domain.model.websocket.Websocket
import me.ibrahimsn.core.presentation.base.BaseViewModel
import me.ibrahimsn.core.presentation.extension.io

class DashboardViewModel(
    getAllRequestsInteractor: Interactor.PagingInteractor<Int, Request>,
    getAllWebsocketsInteractor: Interactor.PagingInteractor<Int, Websocket>,
    private val deleteRequestsInteractor: Interactor.RequestInteractor<DeleteRequestsInteractor.Params, Unit?>,
    private val deleteWebsocketsInteractor: Interactor.RequestInteractor<DeleteWebsocketsInteractor.Params, Unit?>
) : BaseViewModel() {

    private var _activeTab = 0
    var activeTab: Int get() = _activeTab
        set(value) { _activeTab = value }

    private val pagingConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPrefetchDistance(5)
        .setInitialLoadSizeHint(35)
        .setPageSize(25)
        .build()

    val pagedRequestLiveData = LivePagedListBuilder(
        getAllRequestsInteractor.invoke(),
        pagingConfig
    ).build()

    val pagedWebsocketLiveData = LivePagedListBuilder(
        getAllWebsocketsInteractor.invoke(),
        pagingConfig
    ).build()

    fun deleteRequests(requests: List<Request>?) {
        viewModelScope.io {
            deleteRequestsInteractor.invoke(
                DeleteRequestsInteractor.Params(requests)
            )
        }
    }

    fun deleteWebsockets(websockets: List<Websocket>?) {
        viewModelScope.io {
            deleteWebsocketsInteractor.invoke(
                DeleteWebsocketsInteractor.Params(websockets)
            )
        }
    }
}
