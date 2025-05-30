package com.teqie.taskmaster.domain.useCases.schedule


import com.teqie.taskmaster.data.repository.schedule.ScheduleRepository
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.Schedule
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScheduleUseCase @Inject constructor (private val scheduleRepo: ScheduleRepository){
    operator fun invoke(projectId: String): Flow<Resource<List<Schedule>>> {
       return scheduleRepo.fetchProjectSchedule(projectId)
    }
}