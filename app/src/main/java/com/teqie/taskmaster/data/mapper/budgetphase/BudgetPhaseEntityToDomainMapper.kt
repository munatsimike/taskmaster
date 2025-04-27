package com.teqie.taskmaster.data.mapper.budgetphase

import com.teqie.taskmaster.data.local.db.enties.BudgetPhaseEntity
import com.teqie.taskmaster.data.mapper.EntityToDomain
import com.teqie.taskmaster.domain.model.budget.BudgetPhase
import com.teqie.taskmaster.domain.model.teamMember.AssignedTeamMember

object BudgetPhaseEntityToDomainMapper : EntityToDomain<BudgetPhaseEntity, BudgetPhase> {
    override fun BudgetPhaseEntity.toDomainModel(): BudgetPhase {
        return BudgetPhase(
            id = id,
            phase = phase,
            projectId = projectId,
            totalAmount = totalAmount,
            totalPaid = totalPaid,
            initialBudget = initialBudget,
            newBudget = budget,
            assignedTeamMember = AssignedTeamMember(
                userId = userId,
                assignedToUsername = assignedToUsername,
                assignedToName = assignedToName,
                assignedToAvatar = assignedToAvatar
            )
        )
    }
}