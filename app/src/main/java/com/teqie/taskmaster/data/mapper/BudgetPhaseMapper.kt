package com.teqie.taskmaster.data.mapper

import com.teqie.taskmaster.data.remote.dto.dashboard.BudgetPhaseDashBoardDto
import com.teqie.taskmaster.domain.model.budget.DashBoardBudgetPhase

object BudgetPhaseMapper {

    private fun BudgetPhaseDashBoardDto.toDashBoardBudgetPhaseModel(): DashBoardBudgetPhase {
        return DashBoardBudgetPhase(
            id = this.id,
            phase = this.phase,
            projectId = this.id,
            totalAmount = this.totalAmount,
            totalPaid = this.totalPaid
        )
    }

    fun List<BudgetPhaseDashBoardDto?>.toLstOfDashBoardBudgetPhaseModel(): List<DashBoardBudgetPhase> {
        return this.mapNotNull {
            it?.toDashBoardBudgetPhaseModel()
        }
    }
}