package com.teqie.taskmaster.data.repository

import android.util.Log
import com.teqie.taskmaster.domain.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.CancellationException

abstract class BaseRepository {

    fun <DtoModel, EntityModel> processAndCacheApiResponse(
        call: suspend () -> Response<DtoModel>,
        toEntityMapper: (DtoModel) -> List<EntityModel>,
        clearTable: suspend ()-> Unit = {},
        saveEntities: suspend (List<EntityModel>) -> Unit
    ): Flow<Resource<Unit>> = flow {

        emit(Resource.Loading)

        when (val result = safeApiCall(call)) {
            is Resource.Success -> {
                val entities = toEntityMapper(result.data)
                withContext(Dispatchers.IO){
                    clearTable()
                    saveEntities(entities)
                }
                emit(Resource.Success(Unit)) // We don't need to return data here, just success
            }

            is Resource.Failure -> {
                Log.i("called", result.message)
                emit(result)
            }
            is Resource.Error -> result.exception.localizedMessage?.let {
                Resource.Failure(
                    null,
                    it, null
                )
            }
                ?.let { emit(it) } // Should not normally happen if safeApiCall always returns Failure, but safe fallback
            else -> {}
        }
    }

    // used to save dashboard with extra column project Id. the dto does not have project id
    suspend fun <T> safeApiCall(
        call: suspend () -> Response<T>
    ): Resource<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Resource.Success(body)
                } ?: Resource.Failure(null, "Response body is null", null)
            } else {
                Resource.Failure(
                    response.code(),
                    response.message(),
                    response.errorBody()?.string()
                )
            }
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Resource.Failure(null, e.localizedMessage ?: "Network error", null)
        }
    }

    fun <EntityModel, DomainModel> fetchFromLocalDb(
        fetchEntities: () -> Flow<List<EntityModel>>,
        fromEntityMapper: (EntityModel) -> DomainModel
    ): Flow<Resource<List<DomainModel>>> = flow {
        emit(Resource.Loading)

        fetchEntities().collect { entities ->
            if (entities.isEmpty()) {
                emit(Resource.Failure(null, "NO_DATA", null))
            } else {
                val domainModels = entities.map { fromEntityMapper(it) }
                emit(Resource.Success(domainModels))
            }
        }
    }

    // Generic function to process API response and emit a Resource
    fun <ResponseModel, MappedModel> processApiResponse(
        call: suspend () -> Response<ResponseModel>, // Make `call` a suspending function
        onSuccess: (ResponseModel) -> MappedModel
    ): Flow<Resource<MappedModel>> = flow {
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