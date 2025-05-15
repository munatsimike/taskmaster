package com.teqie.taskmaster.domain.useCases.schedule

import com.teqie.taskmaster.data.repository.schedule.ScheduleRepository
import com.teqie.taskmaster.domain.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SyncScheduleToLocalUseCase @Inject constructor(private val  scheduleRepository: ScheduleRepository) {
    operator fun invoke(projectId: String): Flow<Resource<Unit>> {
        return  scheduleRepository.syncScheduleToLocal(projectId)
    }
}