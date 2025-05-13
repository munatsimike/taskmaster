package com.teqie.taskmaster.domain.useCases.budgetPhase

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.budget.BudgetPhase
import com.teqie.taskmaster.domain.model.budget.BudgetPhaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBudgetPhaseUseCase @Inject constructor(private val budgetPhaseRepository: BudgetPhaseRepository) {
    operator fun invoke(projectId: String): Flow<Resource<List<BudgetPhase>>>{
       return  budgetPhaseRepository.fetchBudgetPhases(projectId)
    }
}