package me.ibrahimsn.core.data.model

sealed class ErrorHolder {

    object InvalidParamsError: ErrorHolder()
    object EmptyUriError: ErrorHolder()
    object EmptyBodyError: ErrorHolder()

    object InvalidUriError: ErrorHolder()
    object SocketClosedError: ErrorHolder()

    data class NetworkError(val cause: Throwable): ErrorHolder()
    data class SocketError(val cause: Throwable): ErrorHolder()
}
