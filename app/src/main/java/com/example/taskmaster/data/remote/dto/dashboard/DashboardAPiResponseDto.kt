package com.example.taskmaster.data.remote.dto.dashboard

import com.example.taskmaster.data.remote.dto.orfi.OrfiDto
import com.example.taskmaster.data.remote.dto.schedule.ScheduleFetchResponse

data class DashboardAPiResponseDto(
    val budgetPhases: List<BudgetPhaseDashBoardDto>,
    val orfis: List<OrfiDto>,
    val schedules: List<ScheduleFetchResponse>,
    val totals: TotalsDto
)