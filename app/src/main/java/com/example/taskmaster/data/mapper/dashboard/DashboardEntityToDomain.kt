package com.example.taskmaster.data.mapper.dashboard

import com.example.taskmaster.data.local.db.enties.DashboardEntity
import com.example.taskmaster.data.mapper.BudgetPhaseMapper.toLstOfDashBoardBudgetPhaseModel
import com.example.taskmaster.data.mapper.CommonMapper.toTotalDomain
import com.example.taskmaster.data.mapper.EntityToDomain
import com.example.taskmaster.data.mapper.OrfiMapper.toLstOfOrfiModel
import com.example.taskmaster.data.mapper.ScheduleMapper.toListOfScheduleModel
import com.example.taskmaster.domain.model.DashboardData

object DashboardEntityToDomain: EntityToDomain<DashboardEntity, DashboardData> {
    override fun DashboardEntity.toDomainModel(): DashboardData {
       return DashboardData(
           budgetPhases = budgetPhases.toLstOfDashBoardBudgetPhaseModel(),
           orfis = orfis.toLstOfOrfiModel(),
           schedules = schedules.toListOfScheduleModel(),
           totals = totals.toTotalDomain()
       )
    }
}