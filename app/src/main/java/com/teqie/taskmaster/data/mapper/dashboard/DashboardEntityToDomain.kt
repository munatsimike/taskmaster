package com.teqie.taskmaster.data.mapper.dashboard

import com.teqie.taskmaster.data.local.db.enties.DashboardEntity
import com.teqie.taskmaster.data.mapper.BudgetPhaseMapper.toLstOfDashBoardBudgetPhaseModel
import com.teqie.taskmaster.data.mapper.CommonMapper.toTotalDomain
import com.teqie.taskmaster.data.mapper.EntityToDomain
import com.teqie.taskmaster.data.mapper.OrfiMapper.toLstOfOrfiModel
import com.teqie.taskmaster.data.mapper.schedule.ScheduleCommonMapper.toListOfScheduleModel
import com.teqie.taskmaster.domain.model.DashboardData

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