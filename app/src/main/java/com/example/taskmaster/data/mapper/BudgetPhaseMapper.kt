package com.example.taskmaster.data.mapper

import com.example.taskflow.domain.model.budget.DashBoardBudgetPhase
import com.example.taskmaster.data.remote.dto.dashboard.BudgetPhaseDashBoardDto

object BudgetPhaseMapper {

    private fun BudgetPhaseDashBoardDto.toDashBoardBudgetPhaseModel(): DashBoardBudgetPhase {
        return DashBoardBudgetPhase(
            id = this.id,
            phase = this.phase,
            projectId = this.project_id,
            totalAmount = this.totalAmount,
            totalPaid = this.totalPaid
        )
    }

    fun List<BudgetPhaseDashBoardDto>.toLstOfDashBoardBudgetPhaseModel(): List<DashBoardBudgetPhase> {
        return this.map {
            it.toDashBoardBudgetPhaseModel()
        }
    }
}