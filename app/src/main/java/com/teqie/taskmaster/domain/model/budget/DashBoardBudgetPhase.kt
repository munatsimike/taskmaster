package com.teqie.taskmaster.domain.model.budget

class DashBoardBudgetPhase(
    id: String,
    phase: String,
    projectId: String,
    totalAmount: Int,
    totalPaid: Int
) : BaseBudgetPhase(
    projectId = projectId,
    phase = phase,
    totalPaid = totalPaid,
    totalAmount = totalAmount,
    id = id
)
