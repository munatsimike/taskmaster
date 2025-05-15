package com.teqie.taskmaster.data.repository


import com.example.taskflow.ui.screen.schedule.ScheduleFormState
import com.teqie.taskmaster.data.local.LocalDataSource
import com.teqie.taskmaster.data.remote.RemoteDataSource
import com.teqie.taskmaster.data.repository.budget.BaseRepository
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.Schedule
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
) : BaseRepository(), ScheduleRepository {
    override fun getProjectSchedule(projectId: String): Flow<Resource<List<Schedule>>> =
        processApiResponse(
            call = { remoteDataSource.getProjectSchedule(projectId) }
        ) { schedule ->
            schedule.toListOfScheduleModel()
        }


    override suspend fun updateSchedule(formState: ScheduleFormState): Flow<Resource<ResponseMessage>> {
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