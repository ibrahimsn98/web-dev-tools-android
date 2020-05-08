package me.ibrahimsn.core.presentation.extension

import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView

fun View?.show() {
    this?.visibility = View.VISIBLE
}

fun View?.dismiss() {
    this?.visibility = View.GONE
}

fun View?.showIf(condition: Boolean?) {
    this?.visibility = if (condition == true) View.VISIBLE else View.GONE
}

fun EditText?.setTextSilently(text: String?) {
    this?.tag = "#"
    this?.setText(text)
    this?.tag = null
}

fun CheckBox?.setCheckedSilently(isChecked: Boolean) {
    this?.tag = "#"
    this?.isChecked = isChecked
    this?.tag = null
}

fun TextView?.postText(text: String?) {
    this?.post {
        this.text = text
    }
}