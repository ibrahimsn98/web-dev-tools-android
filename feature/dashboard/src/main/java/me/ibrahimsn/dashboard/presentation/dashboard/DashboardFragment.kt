package me.ibrahimsn.dashboard.presentation.dashboard

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_dashboard.*
import me.ibrahimsn.core.domain.Constants
import me.ibrahimsn.core.domain.model.request.Request
import me.ibrahimsn.core.domain.model.websocket.Websocket
import me.ibrahimsn.core.presentation.base.BaseFragment
import me.ibrahimsn.dashboard.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DashboardFragment : BaseFragment() {

    private val viewModel: DashboardViewModel by sharedViewModel()

    private var actionMode: ActionMode? = null

    private lateinit var requestsAdapter: RequestsAdapter
    private lateinit var websocketsAdapter: WebsocketsAdapter

    override fun layoutRes() = R.layout.fragment_dashboard

    override fun initData() {
        super.initData()
        requestsAdapter = RequestsAdapter(
            onRequestClickListener,
            onRequestLongClickListener,
            onClearSelectedItemsListener
        )

        websocketsAdapter = WebsocketsAdapter(
            onWebsocketClickListener,
            onWebsocketLongClickListener,
            onClearSelectedItemsListener
        )
    }

    override fun initView() {
        tabView.setActiveItem(viewModel.activeTab)
        tabView.onItemSelectedListener = onTabChangedListener
        onTabChangedListener.invoke(viewModel.activeTab)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )

        fab.setOnClickListener {
            when (viewModel.activeTab) {
                TAB_REQUESTS -> navigateToRequest(
                    Request.newInstance(Constants.DEFAULT_REQUEST_URI)
                )
                TAB_WEBSOCKETS -> navigateToWebsocket(
                    Websocket.newInstance(Constants.DEFAULT_WEBSOCKET_URI)
                )
            }
        }
    }

    override fun observeData() {
        viewModel.pagedRequestLiveData.observe(viewLifecycleOwner, Observer {
            requestsAdapter.submitList(it)
        })

        viewModel.pagedWebsocketLiveData.observe(viewLifecycleOwner, Observer {
            websocketsAdapter.submitList(it)
        })
    }

    private val onTabChangedListener = { tab: Int ->
        viewModel.activeTab = tab
        actionMode?.finish()

        when (tab) {
            TAB_REQUESTS -> {
                recyclerView.adapter = requestsAdapter
            }
            TAB_WEBSOCKETS -> {
                recyclerView.adapter = websocketsAdapter
            }
        }
    }

    private val onRequestClickListener = { request: Request?, pos: Int ->
        if (actionMode != null) {
            requestsAdapter.toggleSelection(request, pos)
        } else {
            navigateToRequest(request)
        }
    }

    private val onRequestLongClickListener = { request: Request?, pos: Int ->
        if (actionMode == null) actionMode = activity?.startActionMode(actionModeCallback)
        requestsAdapter.toggleSelection(request, pos)
    }

    private val onWebsocketClickListener = { websocket: Websocket?, pos: Int ->
        if (actionMode != null) {
            websocketsAdapter.toggleSelection(websocket, pos)
        } else {
            navigateToWebsocket(websocket)
        }
    }

    private val onWebsocketLongClickListener = { websocket: Websocket?, pos: Int ->
        if (actionMode == null) actionMode = activity?.startActionMode(actionModeCallback)
        websocketsAdapter.toggleSelection(websocket, pos)
    }

    private val onClearSelectedItemsListener = {
        actionMode?.finish()
    }

    private val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.menu_action, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.delete -> {
                    when (viewModel.activeTab) {
                        TAB_REQUESTS -> viewModel.deleteRequests(
                            requestsAdapter.selectedItems.values.toList()
                        )
                        TAB_WEBSOCKETS -> viewModel.deleteWebsockets(
                            websocketsAdapter.selectedItems.values.toList()
                        )
                    }

                    actionMode?.finish()
                }
            }

            return true
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            requestsAdapter.deselectAll()
            websocketsAdapter.deselectAll()
            actionMode = null
        }
    }

    private fun navigateToRequest(request: Request?) {
        findNavController().navigate(R.id.action_dashboardFragment_to_requestFragment, Bundle().apply {
            putParcelable(ARG_REQUEST, request)
        })
    }

    private fun navigateToWebsocket(websocket: Websocket?) {
        findNavController().navigate(R.id.action_dashboardFragment_to_websocketFragment, Bundle().apply {
            putParcelable(ARG_WEBSOCKET, websocket)
        })
    }

    companion object {
        private const val TAB_REQUESTS = 0
        private const val TAB_WEBSOCKETS = 1
        private const val ARG_REQUEST = "request"
        private const val ARG_WEBSOCKET = "websocket"
    }
}
