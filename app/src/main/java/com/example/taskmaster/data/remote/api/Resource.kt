package com.example.taskmaster.data.remote.api

sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val code: Int?, val message: String, val errorBody: String?) : Resource<Nothing>()
    data class Error(val exception: Throwable) : Resource<Nothing>()
}