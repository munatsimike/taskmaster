package com.teqie.taskmaster.data.mapper

import com.teqie.taskmaster.data.remote.dto.budget.CreateBudgetPhaseDto
import com.teqie.taskmaster.data.remote.dto.budget.UpdateBudgetPhaseDto
import com.teqie.taskmaster.data.remote.dto.dashboard.BudgetPhaseDashBoardDto
import com.teqie.taskmaster.domain.model.budget.BudgetPhaseFormData
import com.teqie.taskmaster.domain.model.budget.DashBoardBudgetPhase
import com.teqie.taskmaster.util.DateFormat
import com.teqie.taskmaster.util.isoToReadableDate

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

    fun BudgetPhaseFormData.toCreateBudgetPhaseDto(): CreateBudgetPhaseDto {
        return CreateBudgetPhaseDto(
            phase = phase,
            project_id = projectId,
            progress = progress.toDoubleOrNull() ?: 0.0,
            total_duration = totalDuration.toIntOrNull() ?: 0,
            budget = initBudget,
            user_id = userId,
            startdate = startDate.isoToReadableDate(DateFormat.SIMPLE)
        )
    }
    fun BudgetPhaseFormData.toUpdateBudgetPhaseDto(): UpdateBudgetPhaseDto {
        return UpdateBudgetPhaseDto(
            id = id,
            phase = phase,
            user_id = userId,
            assignedToName = assignedToName,
            initial_budget = initBudget.toDouble(),
            budget = revisedBudget.toDouble(),
        )
    }

}