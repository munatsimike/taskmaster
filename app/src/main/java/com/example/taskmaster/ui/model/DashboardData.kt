package com.example.taskmaster.ui.model

import com.example.taskflow.domain.model.budget.DashBoardBudgetPhase
import com.example.taskmaster.domain.model.Schedule
import com.example.taskmaster.ui.model.orfi.Orfi

data class DashboardData(
    val budgetPhases: List<DashBoardBudgetPhase?>,
    val orfis: List<Orfi>,
    val schedules: List<Schedule>,
    val totals: Totals
)
