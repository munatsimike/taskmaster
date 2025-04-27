package com.teqie.taskmaster.data.mapper.budgetphase

import com.teqie.taskmaster.data.local.db.enties.BudgetPhaseEntity
import com.teqie.taskmaster.data.mapper.DtoToEntityMapper
import com.teqie.taskmaster.data.remote.dto.budget.BudgetPhaseResponseDto

object BudgetPhaseDtoToEntityMapper: DtoToEntityMapper<BudgetPhaseResponseDto, BudgetPhaseEntity> {
    override fun BudgetPhaseResponseDto.toEntity(projectId: String?): BudgetPhaseEntity {
        return BudgetPhaseEntity(
            id = id,
            phase = phase,
            projectId = requireNotNull(projectId),
            totalAmount = totalAmount,
            totalPaid = totalPaid,
            userId = userId,
            assignedToUsername = assignedToUsername,
            assignedToName = assignedToName,
            assignedToAvatar = assignedToAvatar,
            initialBudget = initialBudget,
            budget = budget
        )
    }
}