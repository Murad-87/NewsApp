package com.example.testapp1.utils

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val localMessage: Int? = null,
) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String?): Resource<T>(message = message)
    class LocalError<T>(localMessage: Int?): Resource<T>(localMessage = localMessage)
    class Loading<T>: Resource<T>()
}