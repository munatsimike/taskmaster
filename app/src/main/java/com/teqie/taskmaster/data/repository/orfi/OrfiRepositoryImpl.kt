package com.teqie.taskmaster.data.repository.orfi

import com.teqie.taskmaster.data.local.LocalDataSource
import com.teqie.taskmaster.data.mapper.orfi.OrfiDtoToEntityMapper.toEntityList
import com.teqie.taskmaster.data.mapper.orfi.OrfiEntityToDomainMapper.toDomainModel
import com.teqie.taskmaster.data.mapper.orfi.OrfiToDtoMapper.toDtoModel
import com.teqie.taskmaster.data.remote.RemoteDataSource
import com.teqie.taskmaster.data.repository.BaseRepository
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.ui.model.ResponseMessage
import com.teqie.taskmaster.ui.model.orfi.Orfi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrfiRepositoryImpl @Inject constructor(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
) : BaseRepository(), OrfiRepository {

    override fun updateORFI(orfi: Orfi): Flow<Resource<ResponseMessage>> =
        processApiResponse(
            call = { remoteDataSource.updateORFI(orfi.id, orfi.toDtoModel()) },
            onSuccess = {
                ResponseMessage(message = "ORFI updated successfully")
            }
        )

    override fun fetchOrfi(projectId: String): Flow<Resource<List<Orfi>>> = flow {
        emitAll(fetchFromLocalDb(
            fetchEntities = { localDataSource.fetchOrfis(projectId) },
            fromEntityMapper = { it.toDomainModel() }
        ))
    }

    override fun createORFI(createORFIRequest: Orfi): Flow<Resource<ResponseMessage>> =
        processApiResponse(
            call = { remoteDataSource.createORFI(createORFIRequest.toDtoModel()) },
            onSuccess = {
                ResponseMessage(message = "ORFI created successfully")
            }
        )

    override fun deleteORFI(orfiId: String): Flow<Resource<ResponseMessage>> = processApiResponse(
        call = { remoteDataSource.deleteORFI(orfiId) },
        onSuccess = { response ->
            response
        }
    )

    override fun syncBudgetPhasesToLocal(projectId: String): Flow<Resource<Unit>> = flow {
        emitAll(processAndCacheApiResponse(
            call = { remoteDataSource.getORFI(projectId) },
            toEntityMapper = { it.toEntityList() },
            saveEntities = { localDataSource.saveOrfi(it) }
        )
        )
    }
}