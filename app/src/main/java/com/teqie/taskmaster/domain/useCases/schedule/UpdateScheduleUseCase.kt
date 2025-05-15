package com.teqie.taskmaster.domain.useCases.schedule

import com.example.taskflow.ui.screen.schedule.ScheduleFormState
import com.teqie.taskmaster.data.repository.schedule.ScheduleRepository
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateScheduleUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
    operator fun invoke(formState: ScheduleFormState): Flow<Resource<ResponseMessage>> {
        return scheduleRepository.updateSchedule(formState)
    }
}