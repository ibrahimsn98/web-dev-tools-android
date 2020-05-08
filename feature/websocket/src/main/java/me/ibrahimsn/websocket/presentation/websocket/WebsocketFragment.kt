package me.ibrahimsn.websocket.presentation.websocket

import android.text.Editable
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_ws.*
import me.ibrahimsn.core.data.model.DefaultTextWatcher
import me.ibrahimsn.core.data.model.DataHolder
import me.ibrahimsn.core.data.model.ErrorHolder
import me.ibrahimsn.core.domain.model.param.Param
import me.ibrahimsn.core.domain.model.websocket.Websocket
import me.ibrahimsn.core.presentation.base.BaseFragment
import me.ibrahimsn.core.presentation.extension.getHeaderParams
import me.ibrahimsn.core.presentation.extension.show
import me.ibrahimsn.core.presentation.extension.showToast
import me.ibrahimsn.core.presentation.extension.toJson
import me.ibrahimsn.core.presentation.ui.param.ParamAdapter
import me.ibrahimsn.websocket.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class WebsocketFragment : BaseFragment() {

    private val viewModel: WebsocketViewModel by viewModel()

    private lateinit var headerParamsAdapter: ParamAdapter

    private var websocket: Websocket? = null

    override fun layoutRes() = R.layout.fragment_ws

    override fun initData() {
        super.initData()

        headerParamsAdapter = ParamAdapter(
            onHeaderParamsUpdatedListener
        )

        websocket = arguments?.getParcelable(ARG_WEBSOCKET)
        viewModel.websocket = websocket
    }

    override fun initView() {
        editTextUri.setText(websocket?.uri)
        editTextUri.addTextChangedListener(uriWatcher)

        recyclerViewHeaderParams.layoutManager = LinearLayoutManager(activity)
        recyclerViewHeaderParams.adapter = headerParamsAdapter.apply {
            setParams(websocket.getHeaderParams())
        }

        buttonNewHeaderParam.setOnClickListener {
            headerParamsAdapter.addParam(Param.newInstance())
        }

        buttonConnect.setOnClickListener {
            if (!buttonConnect.isReversed) {
                viewModel.connectWebsocket(headerParamsAdapter.getParams())
            } else {
                viewModel.disconnectWebsocket()
            }
        }

        buttonClearBody.setOnClickListener {
            editTextBody.setText("")
        }

        buttonSendBody.setOnClickListener {
            viewModel.sendWebSocket(
                editTextBody.text.toString().trim()
            )
        }

        buttonClearResponse.setOnClickListener {
            textViewResponse.text = ""
        }
    }

    override fun observeData() {
        viewModel.websocketIdLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataHolder.Success -> {
                    it.data?.let { id ->
                        websocket?.id = id
                    }
                }
            }
        })

        viewModel.connectionStatusLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataHolder.Loading -> {
                    buttonConnect.isProgressing = true
                }
                is DataHolder.Success -> {
                    buttonConnect.isProgressing = false
                    buttonConnect.isReversed = true
                }
                is DataHolder.Error -> {
                    buttonConnect.isProgressing = false
                    buttonConnect.isReversed = false
                    it.error.let { error ->
                        when (error) {
                            is ErrorHolder.InvalidParamsError -> {
                                showToast(R.string.error_invalid_params)
                            }
                            is ErrorHolder.EmptyUriError -> {
                                showToast(R.string.error_empty_uri)
                            }
                            is ErrorHolder.InvalidUriError -> {
                                showToast(R.string.error_invalid_uri)
                            }
                            is ErrorHolder.SocketError -> {
                                showToast(error.cause.message)
                            }
                        }
                    }
                }
            }
        })

        viewModel.responseLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataHolder.Success -> {
                    layoutResponse.show()
                    textViewResponse.append("${it.data}\n")
                }
            }
        })
    }

    private val onHeaderParamsUpdatedListener = { params: List<Param> ->
        params.toJson()?.let {
            websocket?.headerParams = it
        }
        viewModel.websocket = websocket
        viewModel.updateWebsocket()
    }

    private val uriWatcher = object:
        DefaultTextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            if (editable != null && editTextUri.tag == null) {
                websocket?.uri = editable.toString()
                viewModel.updateWebsocket()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        editTextUri.removeTextChangedListener(uriWatcher)
    }

    companion object {

        private const val ARG_WEBSOCKET = "websocket"
    }
}
