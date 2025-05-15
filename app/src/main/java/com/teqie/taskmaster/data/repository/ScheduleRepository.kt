package com.teqie.taskmaster.data.repository

import com.example.taskflow.ui.screen.schedule.ScheduleFormState
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.Schedule
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun updateSchedule(formState: ScheduleFormState): Flow<Resource<ResponseMessage>>
    fun fetchProjectSchedule(projectId: String): Flow<Resource<List<Schedule>>>
    fun syncBudgetPhasesToLocal(projectId: String): Flow<Resource<Unit>>
}