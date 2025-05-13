package com.teqie.taskmaster.domain.useCases.budgetPhase

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.budget.BudgetPhaseFormData
import com.teqie.taskmaster.domain.model.budget.BudgetPhaseRepository
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateNewBudgetUseCase @Inject constructor(private val budgePhaseRepo: BudgetPhaseRepository) {
    operator  fun invoke(newBudgetPhase: BudgetPhaseFormData): Flow<Resource<ResponseMessage>> {
       return budgePhaseRepo.addNewBudgetPhase(newBudgetPhase)
    }
}