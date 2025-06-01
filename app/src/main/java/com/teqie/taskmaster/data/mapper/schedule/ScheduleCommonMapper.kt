package com.teqie.taskmaster.data.mapper.schedule

import com.teqie.taskmaster.data.remote.dto.schedule.ScheduleResponseDto
import com.teqie.taskmaster.data.remote.dto.schedule.UpdateScheduleRequest
import com.teqie.taskmaster.domain.model.Schedule
import com.teqie.taskmaster.ui.screen.schedule.ScheduleFormState

object ScheduleCommonMapper {

    private fun ScheduleResponseDto.toScheduleModel(): Schedule {
        return Schedule(
            id = this.id,
            isDeleted = false,
            progress = progress,
            projectId = projectId,// Could be a percentage, adjust as needed
            startDate = this.startdate,
            phase = this.phase,// Consider using a Date type if appropriate
            totalDuration = this.total_duration
        )
    }


    fun ScheduleFormState.toUpdateScheduleRequest(): UpdateScheduleRequest {
        return UpdateScheduleRequest(
            startdate = startDate,
            total_duration = totalDuration.toDoubleOrNull()?.toInt()
                ?: 0, // Safely handle conversion
            progress = progress.toDoubleOrNull()?.toInt() ?: 0
        )
    }

    fun List<ScheduleResponseDto>.toListOfScheduleModel(): List<Schedule> {
        return this.map {
            it.toScheduleModel()
        }
    }
}