package com.example.taskmaster.data.mapper

import com.example.taskmaster.data.remote.dto.schedule.ScheduleFetchResponse
import com.example.taskmaster.domain.model.Schedule

object ScheduleMapper {
    private fun ScheduleFetchResponse.toScheduleModel(): Schedule {
        return Schedule(
            id = this.id,
            isDeleted = false,
            progress = progress,
            project_id = project_id,// Could be a percentage, adjust as needed
            startDate = this.startdate,
            phase = this.phase,// Consider using a Date type if appropriate
            totalDuration = this.total_duration
        )
    }

    fun List<ScheduleFetchResponse>.toListOfScheduleModel(): List<Schedule> {
        return this.map {
            it.toScheduleModel()
        }
    }
}