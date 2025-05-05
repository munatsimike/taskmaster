package com.teqie.taskmaster.domain.useCases.budgetPhase

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.buget.BudgetPhaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SyncBudgetPhasesToLocalUseCase @Inject constructor( private val budgetPhaseRepository: BudgetPhaseRepository){
    operator fun invoke(projectId: String): Flow<Resource<Unit>> {
        return  budgetPhaseRepository.syncBudgetPhasesToLocal(projectId)
    }
}