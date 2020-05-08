package me.ibrahimsn.core.presentation.ui.param

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.row_param.view.*
import me.ibrahimsn.core.R
import me.ibrahimsn.core.domain.model.param.Param
import me.ibrahimsn.core.presentation.base.BaseAdapter
import me.ibrahimsn.core.presentation.base.BaseViewHolder
import me.ibrahimsn.core.presentation.extension.setCheckedSilently
import me.ibrahimsn.core.presentation.extension.setTextSilently

class ParamAdapter(
    private val onParamsUpdatedListener: (List<Param>) -> Unit?
) : BaseAdapter<Param, ParamAdapter.ParamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_param, parent, false)
        return ParamViewHolder(view)
    }

    fun addParam(param: Param) {
        items.add(param)
        notifyItemInserted(itemCount - 1)
    }

    fun setParams(params: List<Param>?) {
        params?.let {
            items.clear()
            items.addAll(params)
            notifyDataSetChanged()
        }
    }

    fun getParams(): List<Param> {
        return items
    }

    override fun areItemsTheSame(oldItem: Param, newItem: Param): Boolean {
        return oldItem.key == newItem.key
    }

    override fun areContentsTheSame(oldItem: Param, newItem: Param): Boolean {
        return oldItem.value == newItem.value
    }

    inner class ParamViewHolder(private val view: View) : BaseViewHolder<Param>(view) {

        override fun bind(item: Param?) {
            super.bind(item)

            item?.let {
                view.cbActive.setCheckedSilently(it.isActive)
                view.etKey.setTextSilently(it.key)
                view.etValue.setTextSilently(it.value)
            }
        }

        init {
            view.cbActive.setOnCheckedChangeListener { _, isChecked ->
                boundItem?.apply {
                    if (view.cbActive.tag == null) {
                        isActive = isChecked
                        onParamsUpdatedListener.invoke(items)
                    }
                }
            }

            // May cause memory leaks! Needs refactor
            view.etKey.addTextChangedListener {
                boundItem?.apply {
                    if (view.etKey.tag == null) {
                        key = it.toString()
                        onParamsUpdatedListener.invoke(items)
                    }
                }
            }

            view.etValue.addTextChangedListener {
                boundItem?.apply {
                    if (view.etValue.tag == null) {
                        value = it.toString()
                        onParamsUpdatedListener.invoke(items)
                    }
                }
            }

            view.ibRemove.setOnClickListener {
                boundItem?.apply {
                    val index = items.indexOf(this)
                    items.removeAt(index)
                    notifyItemRemoved(index)
                    onParamsUpdatedListener(items)
                }
            }
        }
    }
}
