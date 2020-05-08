package me.ibrahimsn.request.presentation.request

import android.text.Editable
import android.view.MenuItem
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_rest.*
import me.ibrahimsn.core.data.model.DefaultTextWatcher
import me.ibrahimsn.core.data.model.DataHolder
import me.ibrahimsn.core.data.model.ErrorHolder
import me.ibrahimsn.core.data.model.RequestMethod
import me.ibrahimsn.core.domain.model.param.Param
import me.ibrahimsn.core.domain.model.request.Request
import me.ibrahimsn.core.presentation.base.BaseFragment
import me.ibrahimsn.core.presentation.extension.*
import me.ibrahimsn.core.presentation.ui.param.ParamAdapter
import me.ibrahimsn.request.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class RequestFragment : BaseFragment() {

    private val viewModel: RequestViewModel by viewModel()

    private lateinit var queryParamsAdapter: ParamAdapter
    private lateinit var headerParamsAdapter: ParamAdapter
    private lateinit var bodyParamsAdapter: ParamAdapter

    private var request: Request? = null

    override fun layoutRes() = R.layout.fragment_rest

    override fun initData() {
        super.initData()

        queryParamsAdapter = ParamAdapter(
            onQueryParamsUpdatedListener
        )

        headerParamsAdapter = ParamAdapter(
            onHeaderParamsUpdatedListener
        )

        bodyParamsAdapter = ParamAdapter(
            onBodyParamsUpdatedListener
        )

        request = arguments?.getParcelable(ARG_REQUEST)
        viewModel.request = request
        viewModel.parseUriParams(request?.uri)
    }

    override fun initView() {
        editTextUri.setText(request?.uri)
        editTextUri.addTextChangedListener(uriWatcher)

        textViewMethod.text = request?.method?.name
        layoutBodyParams.showIf(request?.method?.hasBody)

        recyclerViewQueryParams.layoutManager = LinearLayoutManager(activity)
        recyclerViewQueryParams.adapter = queryParamsAdapter

        recyclerViewHeaderParams.layoutManager = LinearLayoutManager(activity)
        recyclerViewHeaderParams.adapter = headerParamsAdapter.apply {
            setParams(request.getHeaderParams())
        }

        recyclerViewBodyParams.layoutManager = LinearLayoutManager(activity)
        recyclerViewBodyParams.adapter = bodyParamsAdapter.apply {
            setParams(request.getBodyParams())
        }

        buttonNewQueryParam.setOnClickListener {
            queryParamsAdapter.addParam(Param.newInstance())
        }

        buttonNewHeaderParam.setOnClickListener {
            headerParamsAdapter.addParam(Param.newInstance())
        }

        buttonNewBodyParam.setOnClickListener {
            bodyParamsAdapter.addParam(Param.newInstance())
        }

        buttonExecute.setOnClickListener {
            viewModel.executeRequest(
                headerParamsAdapter.getParams(),
                bodyParamsAdapter.getParams()
            )
        }

        textViewMethod.setOnClickListener {
            activity?.let { activity ->
                val popup = PopupMenu(activity, it)
                popup.menuInflater.inflate(R.menu.menu_request_method, popup.menu)
                popup.setOnMenuItemClickListener(onPopupItemSelectedListener)
                popup.show()
            }
        }
    }

    override fun observeData() {
        viewModel.requestIdLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataHolder.Success -> {
                    it.data?.let { data ->
                        request?.id = data
                    }
                }
            }
        })

        viewModel.uriLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataHolder.Success -> {
                    it.data?.let { uri ->
                        request?.uri = uri.toString()
                        editTextUri.setTextSilently(request?.uri)
                        viewModel.updateRequest()
                    }
                }
            }
        })

        viewModel.queryParamsLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataHolder.Success -> {
                    it.data?.let { params ->
                        queryParamsAdapter.setParams(params)
                    }
                }
            }
        })

        viewModel.responseLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataHolder.Loading -> {
                    buttonExecute.isProgressing = true
                }
                is DataHolder.Success -> {
                    buttonExecute.isProgressing = false
                    it.data?.let { response ->
                        layoutResponse.show()
                        textViewResponseCode.text = response.code.toString()
                        textViewResponse.postText(response.body)
                    }
                }
                is DataHolder.Error -> {
                    buttonExecute.isProgressing = false
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
                            is ErrorHolder.EmptyBodyError -> {
                                showToast(R.string.error_empty_request_body)
                            }
                            is ErrorHolder.NetworkError -> {
                                showToast(error.cause.message)
                            }
                        }
                    }
                }
            }
        })
    }

    private val onQueryParamsUpdatedListener = { params: List<Param> ->
        viewModel.generateUri(editTextUri.text.toString(), params)
    }

    private val onHeaderParamsUpdatedListener = { params: List<Param> ->
        params.toJson()?.let {
            request?.headerParams = it
        }
        viewModel.request = request
        viewModel.updateRequest()
    }

    private val onBodyParamsUpdatedListener = { params: List<Param> ->
        params.toJson()?.let {
            request?.bodyParams = it
        }
        viewModel.request = request
        viewModel.updateRequest()
    }

    private val onPopupItemSelectedListener: (MenuItem) -> Boolean = { item ->
        val requestMethod = when (item.itemId) {
            R.id.methodGet -> RequestMethod.GET
            R.id.methodPost -> RequestMethod.POST
            R.id.methodPut -> RequestMethod.PUT
            R.id.methodDelete -> RequestMethod.DEL
            R.id.methodPatch -> RequestMethod.PATCH
            else -> RequestMethod.GET
        }

        textViewMethod.text = requestMethod.name
        layoutBodyParams.showIf(requestMethod.hasBody)
        request?.method = requestMethod
        viewModel.updateRequest()
        true
    }

    private val uriWatcher = object:
        DefaultTextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            if (editable != null && editTextUri.tag == null) {
                request?.uri = editable.toString()
                viewModel.parseUriParams(editable.toString())
                viewModel.updateRequest()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        editTextUri.removeTextChangedListener(uriWatcher)
    }

    companion object {

        private const val ARG_REQUEST = "request"
    }
}
