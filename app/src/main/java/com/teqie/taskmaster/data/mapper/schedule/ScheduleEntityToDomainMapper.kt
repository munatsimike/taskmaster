package com.teqie.taskmaster.data.mapper.schedule

import com.teqie.taskmaster.data.local.db.enties.ScheduleEntity
import com.teqie.taskmaster.data.mapper.EntityToDomain
import com.teqie.taskmaster.domain.model.Schedule

object ScheduleEntityToDomainMapper : EntityToDomain<ScheduleEntity, Schedule> {
    override fun ScheduleEntity.toDomainModel(): Schedule {
        return Schedule(
            id = id,
            isDeleted = isDeleted,
            phase = phase,
            progress = progress,
            projectId = projectId,
            startDate = startDate,
            totalDuration = totalDuration  //
        )
    }
}