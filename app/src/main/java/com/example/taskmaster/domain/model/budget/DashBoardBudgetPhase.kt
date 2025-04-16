package com.example.taskflow.domain.model.budget

data class DashBoardBudgetPhase(
    override val id: String,
    override val phase: String,
    override val projectId: String,
    override val totalAmount: Int,
    override val totalPaid: Int
) : BaseBudgetPhase(
    projectId = projectId,
    phase = phase,
    totalPaid = totalPaid,
    totalAmount = totalAmount,
    id = id
)
