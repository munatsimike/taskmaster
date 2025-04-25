package com.teqie.taskmaster.data.mapper

import com.teqie.taskmaster.data.remote.dto.schedule.ScheduleResponseDto
import com.teqie.taskmaster.domain.model.Schedule

object ScheduleMapper {

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

    fun List<ScheduleResponseDto>.toListOfScheduleModel(): List<Schedule> {
        return this.map {
            it.toScheduleModel()
        }
    }
}