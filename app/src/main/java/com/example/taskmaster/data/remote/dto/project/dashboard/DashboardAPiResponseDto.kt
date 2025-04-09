package com.example.taskmaster.data.remote.dto.project.dashboard

import com.example.taskmaster.data.remote.dto.project.orfi.OrfiDto
import com.example.taskmaster.data.remote.dto.project.schedule.ScheduleFetchResponse

data class DashboardAPiResponseDto(
    val budgetPhases: List<BudgetPhaseDashBoardDto>,
    val orfis: List<OrfiDto>,
    val schedules: List<ScheduleFetchResponse>,
    val totals: TotalsDto
)