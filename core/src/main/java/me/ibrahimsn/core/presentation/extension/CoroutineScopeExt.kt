package me.ibrahimsn.core.presentation.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun CoroutineScope.io(codeBlock: suspend CoroutineScope.() -> Unit) {
    launch(Dispatchers.IO) {
        codeBlock()
    }
}