package com.example.taskmaster.data.mapper.dashboard

import com.example.taskmaster.data.local.db.enties.DashboardEntity
import com.example.taskmaster.data.mapper.DtoToEntityMapper
import com.example.taskmaster.data.remote.dto.dashboard.DashboardAPiResponseDto

object DashboardDtoToEntity: DtoToEntityMapper<DashboardAPiResponseDto, DashboardEntity> {
    override fun DashboardAPiResponseDto.toEntity(): DashboardEntity {
        return DashboardEntity(
            budgetPhases = budgetPhases,
            orfis = orfis,
            schedules = schedules,
            totals = totals
        )
    }
}