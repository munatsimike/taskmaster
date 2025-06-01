package com.teqie.taskmaster.data.repository.schedule


import com.teqie.taskmaster.data.local.LocalDataSource
import com.teqie.taskmaster.data.mapper.schedule.ScheduleCommonMapper.toUpdateScheduleRequest
import com.teqie.taskmaster.data.mapper.schedule.ScheduleDtoToEntityMapper.toEntityList
import com.teqie.taskmaster.data.mapper.schedule.ScheduleEntityToDomainMapper.toDomainModel
import com.teqie.taskmaster.data.remote.RemoteDataSource
import com.teqie.taskmaster.data.repository.BaseRepository
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.Schedule
import com.teqie.taskmaster.ui.model.ResponseMessage
import com.teqie.taskmaster.ui.screen.schedule.ScheduleFormState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
) : BaseRepository(), ScheduleRepository {

    override fun fetchProjectSchedule(projectId: String): Flow<Resource<List<Schedule>>> = flow {
        emitAll(fetchFromLocalDb(
            fetchEntities = { localDataSource.fetchProjectSchedule(projectId) },
            fromEntityMapper = { it.toDomainModel() }
        ))
    }

    override fun syncScheduleToLocal(projectId: String): Flow<Resource<Unit>> = flow {
        emitAll(processAndCacheApiResponse(
            call = { remoteDataSource.getProjectSchedule(projectId) },
            toEntityMapper = { it.toEntityList() },
            saveEntities = { localDataSource.saveProjectSchedule(it) }
        )
        )
    }

    override fun updateSchedule(formState: ScheduleFormState): Flow<Resource<ResponseMessage>> {
        return processApiResponse(
            call = {
                remoteDataSource.updateSchedule(
                    formState.id,
                    formState.toUpdateScheduleRequest()
                )
            }, // Make the API call
            onSuccess = { response ->
                ResponseMessage(response.message)
            }
        )
    }
}