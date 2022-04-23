package com.nyt.nytnews.utils

sealed class ResponseIo<out T> {
    data class Error(
        val message: String? = null,
        val error: Throwable? = null
    ): ResponseIo<Nothing>()
    data class Data<out T>(val data: T): ResponseIo<T>()
    object Empty: ResponseIo<Nothing>()
    object Loading: ResponseIo<Nothing>()
}