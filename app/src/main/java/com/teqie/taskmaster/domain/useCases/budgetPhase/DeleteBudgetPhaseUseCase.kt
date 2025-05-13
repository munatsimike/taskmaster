package com.teqie.taskmaster.domain.useCases.budgetPhase


import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.budget.BudgetPhaseRepository
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteBudgetPhaseUseCase @Inject constructor(val budgetPhaseRepo: BudgetPhaseRepository) {
     operator fun invoke(budgetPhaseId: String): Flow<Resource<ResponseMessage>> {
      return  budgetPhaseRepo.deleteBudgetPhase(budgetPhaseId)
    }
}