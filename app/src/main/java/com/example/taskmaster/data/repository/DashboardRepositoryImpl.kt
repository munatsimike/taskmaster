package com.example.taskmaster.data.repository

import android.util.Log
import com.example.taskmaster.data.local.LocalDataSource
import com.example.taskmaster.data.mapper.dashboard.DashboardDtoToEntity.toEntity
import com.example.taskmaster.data.mapper.dashboard.DashboardEntityToDomain.toDomainModel
import com.example.taskmaster.data.remote.RemoteDataSource
import com.example.taskmaster.data.remote.api.Resource
import com.example.taskmaster.domain.DashboardRepository
import com.example.taskmaster.domain.model.DashboardData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DashboardRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : DashboardRepository, BaseRepository() {

    override fun fetchDashboard(projectId: String): Flow<Resource<DashboardData>> = flow {
        localDataSource.getDashBoard(projectId).collect { entity ->
            if (entity != null) {
                emit(Resource.Success(entity.toDomainModel()))
            }else {
                emit(Resource.Error(Throwable("NO_DATA")))
            }
        }
    }

    // 2. Update local database from remote
    override fun updateDashboard(projectId: String): Flow<Resource<Unit>> = flow {
        when (val result = safeApiCall(call = { remoteDataSource.getDashboard(projectId) })) {
            is Resource.Success -> {
                val entities = (result.data).toEntity(projectId)
                localDataSource.saveDashboard(entities)
                emit(Resource.Success(Unit)) // We don't need to return data here, just success
            }

            is Resource.Failure -> {
                emit(result)
            }

            is Resource.Error -> result.exception.localizedMessage?.let {
                Resource.Failure(
                    null,
                    it, null
                )
            }
                ?.let {
                    emit(it)
                }

            else -> {}
        }
    }
}