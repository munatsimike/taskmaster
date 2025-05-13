package com.teqie.taskmaster.domain.useCases.budgetPhase

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.budget.BudgetPhaseFormData
import com.teqie.taskmaster.domain.model.budget.BudgetPhaseRepository
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateBudgetPhaseUseCase @Inject constructor(val budgetPhaseRepo: BudgetPhaseRepository) {
    suspend operator fun invoke(budgetPhase: BudgetPhaseFormData): Flow<Resource<ResponseMessage>> {
        return budgetPhaseRepo.updateBudgetPhase(budgetPhase)
    }
}