package me.ibrahimsn.dashboard.presentation.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.synthetic.main.row_ws.view.*
import me.ibrahimsn.core.domain.model.websocket.Websocket
import me.ibrahimsn.core.presentation.base.BasePagedAdapter
import me.ibrahimsn.core.presentation.base.BaseViewHolder
import me.ibrahimsn.core.presentation.extension.toDateString
import me.ibrahimsn.dashboard.R

class WebsocketsAdapter(
    private val onWebsocketClickListener: (Websocket?, Int) -> Unit?,
    private val onWebsocketLongClickListener: (Websocket?, Int) -> Unit?,
    private val onClearSelectedItemsListener: () -> Unit?
) : BasePagedAdapter<Websocket, WebsocketsAdapter.WebsocketsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebsocketsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_ws, parent, false)
        return WebsocketsViewHolder(view)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?: 0
    }

    override fun onClearSelectedItems() {
        onClearSelectedItemsListener.invoke()
    }

    inner class WebsocketsViewHolder (private val view: View) : BaseViewHolder<Websocket>(view) {

        init {
            itemView.setOnClickListener {
                onWebsocketClickListener.invoke(boundItem, adapterPosition)
            }

            itemView.setOnLongClickListener {
                onWebsocketLongClickListener.invoke(boundItem, adapterPosition)
                true
            }
        }

        override fun bind(item: Websocket?) {
            super.bind(item)

            view.tvUri.text = boundItem?.uri
            view.tvTime.text = boundItem?.createdAt.toDateString()

            itemView.isSelected = isSelectedItem(adapterPosition)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Websocket>() {

        override fun areItemsTheSame(oldItem: Websocket, newItem: Websocket): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Websocket, newItem: Websocket): Boolean {
            return oldItem == newItem
        }
    }
}
