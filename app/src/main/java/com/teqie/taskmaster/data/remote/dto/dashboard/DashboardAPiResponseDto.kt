package com.teqie.taskmaster.data.remote.dto.dashboard

import com.teqie.taskmaster.data.remote.dto.orfi.OrfiResponseDto
import com.teqie.taskmaster.data.remote.dto.schedule.ScheduleResponseDto
import kotlinx.serialization.Serializable

@Serializable
data class DashboardAPiResponseDto(
    val budgetPhases: List<BudgetPhaseDashBoardDto>,
    val orfis: List<OrfiResponseDto>,
    val schedules: List<ScheduleResponseDto>,
    val totals: TotalsResponseDto
)