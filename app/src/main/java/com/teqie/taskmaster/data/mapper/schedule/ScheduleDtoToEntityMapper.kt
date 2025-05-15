package com.teqie.taskmaster.data.mapper.schedule

import com.teqie.taskmaster.data.local.db.enties.ScheduleEntity
import com.teqie.taskmaster.data.mapper.DtoToEntityMapper
import com.teqie.taskmaster.data.remote.dto.schedule.ScheduleFetchResponse

object ScheduleDtoToEntityMapper: DtoToEntityMapper<ScheduleFetchResponse, ScheduleEntity> {
    override fun ScheduleFetchResponse.toEntity(projectId: String?): ScheduleEntity {
        return ScheduleEntity(
            id = id,
            isDeleted = isDeleted,
            phase = phase,
            progress = progress,
            projectId = this.projectId,
            startDate = startdate,
            totalDuration = totalDuration
        )
    }
}