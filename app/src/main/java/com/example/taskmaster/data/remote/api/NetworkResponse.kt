package com.example.taskmaster.data.remote.api

sealed class NetworkResponse<out T> {
    data object Loading : NetworkResponse<Nothing>()
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Failure(val code: Int?, val message: String, val errorBody: String?) : NetworkResponse<Nothing>()
    data class Error(val exception: Throwable) : NetworkResponse<Nothing>()
}