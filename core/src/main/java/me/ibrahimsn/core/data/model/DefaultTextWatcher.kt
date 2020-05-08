package me.ibrahimsn.core.data.model

import android.text.Editable
import android.text.TextWatcher

interface DefaultTextWatcher : TextWatcher {

    override fun afterTextChanged(editable: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }
}
