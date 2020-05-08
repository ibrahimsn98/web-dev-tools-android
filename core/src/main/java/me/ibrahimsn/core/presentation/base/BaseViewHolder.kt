package me.ibrahimsn.core.presentation.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    protected var boundItem: T? = null

    open fun bind(item: T?) {
        boundItem = item
    }
}
