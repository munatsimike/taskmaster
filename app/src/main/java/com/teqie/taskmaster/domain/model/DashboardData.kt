package com.teqie.taskmaster.domain.model


import com.teqie.taskmaster.domain.model.budget.DashBoardBudgetPhase
import com.teqie.taskmaster.ui.model.orfi.Orfi

data class DashboardData(
    val budgetPhases: List<DashBoardBudgetPhase?>,
    val orfis: List<Orfi>,
    val schedules: List<Schedule>,
    val totals: Totals
)
