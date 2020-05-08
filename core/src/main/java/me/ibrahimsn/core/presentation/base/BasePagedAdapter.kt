package me.ibrahimsn.core.presentation.base

import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BasePagedAdapter<T, VH>(diffCallback: DiffUtil.ItemCallback<T>)
    : PagedListAdapter<T, VH>(diffCallback) where VH: BaseViewHolder<T> {

    private val _selectedItems = mutableMapOf<Int, T>()
    val selectedItems: Map<Int, T> get() = _selectedItems

    abstract fun onClearSelectedItems()

    fun isSelectedItem(pos: Int): Boolean = _selectedItems.containsKey(pos)

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    fun deselectAll() {
        val iterator = _selectedItems.iterator()
        while (iterator.hasNext()) {
            val selectedItem = iterator.next()
            if (_selectedItems.containsKey(selectedItem.key)) {
                notifyItemChanged(selectedItem.key)
                iterator.remove()
            }
        }
    }

    fun deselectItem(pos: Int) {
        _selectedItems.remove(pos)
        notifyItemChanged(pos)
    }

    fun toggleSelection(request: T?, pos: Int) {
        if (_selectedItems.containsKey(pos)) {
            _selectedItems.remove(pos)
            if (_selectedItems.isEmpty()) {
                onClearSelectedItems()
            }
        } else {
            request?.let {
                _selectedItems[pos] = it
            }
        }

        notifyItemChanged(pos)
    }
}
