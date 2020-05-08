package me.ibrahimsn.dashboard.presentation.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.synthetic.main.row_rest.view.*
import me.ibrahimsn.core.domain.model.request.Request
import me.ibrahimsn.core.presentation.base.BasePagedAdapter
import me.ibrahimsn.core.presentation.base.BaseViewHolder
import me.ibrahimsn.core.presentation.extension.toDateString
import me.ibrahimsn.dashboard.R

class RequestsAdapter(
    private val onRequestClickListener: (Request?, Int) -> Unit?,
    private val onRequestLongClickListener: (Request?, Int) -> Unit?,
    private val onClearSelectedItemsListener: () -> Unit?
) : BasePagedAdapter<Request, RequestsAdapter.RequestsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_rest, parent, false)
        return RequestsViewHolder(view)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?: 0
    }

    override fun onClearSelectedItems() {
        onClearSelectedItemsListener.invoke()
    }

    inner class RequestsViewHolder (private val view: View) : BaseViewHolder<Request>(view) {

        init {
            itemView.setOnClickListener {
                onRequestClickListener.invoke(boundItem, adapterPosition)
            }

            itemView.setOnLongClickListener {
                onRequestLongClickListener.invoke(boundItem, adapterPosition)
                true
            }
        }

        override fun bind(item: Request?) {
            super.bind(item)

            view.tvUri.text = boundItem?.uri
            view.tvTime.text = boundItem?.createdAt.toDateString()
            view.textViewMethod.text = boundItem?.method?.shortName

            boundItem?.method?.colorRes?.let {
                view.textViewMethod.setTextColor(
                    ContextCompat.getColor(itemView.context, it)
                )
            }

            boundItem?.method?.backgroundRes?.let {
                view.textViewMethod.setBackgroundResource(it)
            }

            itemView.isSelected = isSelectedItem(adapterPosition)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Request>() {

        override fun areItemsTheSame(oldItem: Request, newItem: Request): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Request, newItem: Request): Boolean {
            return oldItem.uri == newItem.uri &&
                    oldItem.method == newItem.method &&
                    oldItem.headerParams == newItem.headerParams &&
                    oldItem.bodyParams == newItem.bodyParams
        }
    }
}
