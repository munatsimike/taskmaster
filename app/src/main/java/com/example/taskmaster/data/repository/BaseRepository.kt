package com.example.taskmaster.data.repository

import com.example.taskmaster.data.remote.RemoteDataSource
import com.example.taskmaster.data.remote.api.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.CancellationException

abstract class BaseRepository(
    protected open val remoteDataSource: RemoteDataSource
){

    fun <T, R, E> processAndCacheApiResponse(
        call: suspend () -> Response<T>,
        toEntityMapper: (T) -> List<E>,
        saveEntities: suspend (List<E>) -> Unit,
        fromEntityMapper: (E) -> R,
        fetchEntities: () -> Flow<List<E>>
    ): Flow<Resource<List<R>>> = flow {
        emit(Resource.Loading)

        val response = call()

        if (response.isSuccessful) {
            response.body()?.let { body ->
                val entities = toEntityMapper(body)
                saveEntities(entities) // Save to DB
            } ?: emit(Resource.Failure(null, "Response body is null", null))
        } else {
            emit(Resource.Failure(response.code(), response.message(), response.errorBody()?.string()))
        }

        fetchEntities().collect { entities ->
            val domainModels = entities.map { fromEntityMapper(it) }
            emit(Resource.Success(domainModels))
        }
    }.catch { e ->
        if (e is CancellationException) throw e
        Timber.e(e, "Error in processAndCacheApiResponse")
        emit(Resource.Error(e))
    }

    // Generic function to process API response and emit a Resource
    fun <T, R> processApiResponse(
        call: suspend () -> Response<T>, // Make `call` a suspending function
        onSuccess: (T) -> R
    ): Flow<Resource<R>> = flow {
        // Call the suspending function inside the flow
        val result = call()
        if (result.isSuccessful) {
            result.body()?.let { body ->
                emit(Resource.Success(onSuccess(body))) // Emit Success with transformed data
            } ?: emit(
                Resource.Failure(
                    null, "Response body is null", null
                )
            ) // Handle null body
        } else {
            emit(
                Resource.Failure(
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
            emit(Resource.Error(exception = e)) // Emit error if a non-cancellation exception is caught
        }
    }
}