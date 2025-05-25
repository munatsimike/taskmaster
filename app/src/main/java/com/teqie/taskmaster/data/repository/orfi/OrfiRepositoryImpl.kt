package com.teqie.taskmaster.data.repository.orfi

import com.teqie.taskmaster.data.local.LocalDataSource
import com.teqie.taskmaster.data.local.db.enties.OrfiEntity
import com.teqie.taskmaster.data.mapper.orfi.OrfiDtoToEntityMapper.toEntityList
import com.teqie.taskmaster.data.mapper.orfi.OrfiToDtoMapper.toDtoModel
import com.teqie.taskmaster.data.remote.RemoteDataSource
import com.teqie.taskmaster.data.repository.budget.BaseRepository
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.ui.model.ResponseMessage
import com.teqie.taskmaster.ui.model.orfi.Orfi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrfiRepositoryImpl @Inject constructor(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
): BaseRepository(), OrfiRepository{

    override fun updateORFI(orfi: Orfi): Flow<Resource<ResponseMessage>> =
        processApiResponse(
            call = { remoteDataSource.updateORFI(orfi.id, orfi.toDtoModel()) },
            onSuccess = {
                ResponseMessage(message = "ORFI updated successfully")
            }
        )

    override fun getORFI(projectId: String): Flow<Resource<List<OrfiEntity>>> = processApiResponse(
        call = { remoteDataSource.getORFI(projectId) }
    ) { orfi ->
        orfi.toEntityList()
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
}