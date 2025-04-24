package com.example.taskmaster.domain.model


import com.example.taskmaster.domain.model.budget.DashBoardBudgetPhase
import com.example.taskmaster.ui.model.orfi.Orfi

data class DashboardData(
    val budgetPhases: List<DashBoardBudgetPhase?>,
    val orfis: List<Orfi>,
    val schedules: List<Schedule>,
    val totals: Totals
)
