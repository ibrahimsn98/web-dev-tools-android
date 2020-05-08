package me.ibrahimsn.core.domain.model.request

data class RequestResponse(
    val status: Boolean,
    val code: Int,
    val body: String?
)
