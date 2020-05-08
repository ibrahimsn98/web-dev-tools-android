package me.ibrahimsn.core.presentation.extension

import android.widget.Toast
import androidx.annotation.StringRes
import me.ibrahimsn.core.presentation.base.BaseFragment

fun BaseFragment.showToast(@StringRes textRes: Int) {
    this.activity?.let {
        Toast.makeText(it, textRes, Toast.LENGTH_SHORT).show()
    }
}

fun BaseFragment.showToast(text: String?) {
    this.activity?.let {
        Toast.makeText(it, text, Toast.LENGTH_SHORT).show()
    }
}