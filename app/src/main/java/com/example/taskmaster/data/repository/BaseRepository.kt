package com.example.taskmaster.data.repository

import com.example.taskmaster.data.remote.RemoteDataSource
import com.example.taskmaster.data.remote.api.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.CancellationException

abstract class BaseRepository(
    protected open val remoteDataSource: RemoteDataSource
){
    // Generic function to process API response and emit a NetworkResponse
    fun <T, R> processApiResponse(
        call: suspend () -> Response<T>, // Make `call` a suspending function
        onSuccess: (T) -> R
    ): Flow<NetworkResponse<R>> = flow {
        // Call the suspending function inside the flow
        val result = call()
        if (result.isSuccessful) {
            result.body()?.let { body ->
                emit(NetworkResponse.Success(onSuccess(body))) // Emit Success with transformed data
            } ?: emit(
                NetworkResponse.Failure(
                    null, "Response body is null", null
                )
            ) // Handle null body
        } else {
            emit(
                NetworkResponse.Failure(
                    result.code(), result.message(), result.errorBody()?.string()
                )
            ) // Emit failure with details
        }
    }.catch { e ->
        // Handle CancellationException separately to avoid treating it as a failure
        if (e is CancellationException) {
            // Log cancellation or handle it if necessary
            Timber.tag("processApiResponse").w("Request was canceled: ${e.localizedMessage}")
            throw e // Rethrow to properly cancel the coroutine
        } else {
            // Log other exceptions
            Timber.tag("processApiResponse").e(e, "Error in network call: ${e.localizedMessage}")
            emit(NetworkResponse.Error(exception = e)) // Emit error if a non-cancellation exception is caught
        }
    }
}