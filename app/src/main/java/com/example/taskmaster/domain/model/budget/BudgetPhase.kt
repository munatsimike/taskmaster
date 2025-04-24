package com.example.taskflow.domain.model.budget

import com.example.taskmaster.domain.model.budget.BaseBudgetPhase
import com.example.taskmaster.domain.model.teamMember.AssignedTeamMember


class BudgetPhase (
    override val id: String,
    override val phase: String,
    override val projectId: String,
    override val totalAmount: Int,
    override val totalPaid: Int,
    val initialBudget:Double,
    val newBudget:Double,
    val assignedTeamMember: AssignedTeamMember
): BaseBudgetPhase(
    projectId = projectId,
    phase = phase,
    totalPaid = totalPaid,
    totalAmount = totalAmount,
    id = id
)
{
    val budgetSpent: Float =  totalAmount/ newBudget.toFloat()
    val budgetSpentPercentage  = (budgetSpent * 100).toInt()
}