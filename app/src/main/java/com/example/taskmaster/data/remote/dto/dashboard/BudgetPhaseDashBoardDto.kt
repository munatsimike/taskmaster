package com.example.taskmaster.data.remote.dto.dashboard

import com.example.taskmaster.data.remote.dto.budget.BaseBudgetPhaseDto

data class BudgetPhaseDashBoardDto(
    override val id: String,
    override val phase: String,
    override val project_id: String,
    override val totalAmount: Int,
    override val totalPaid: Int
) : BaseBudgetPhaseDto(
    phase = phase,
    project_id = project_id,
    id = id,
    totalAmount = totalAmount,
    totalPaid = totalPaid
)