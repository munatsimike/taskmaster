package com.teqie.taskmaster.domain.buget

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.budget.BudgetPhase
import com.teqie.taskmaster.domain.model.budget.BudgetPhaseFormData
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow

interface BudgetPhaseRepository {

    fun fetchBudgetPhases(projectId: String): Flow<Resource<List<BudgetPhase>>>

    suspend fun updateBudgetPhase(budgetPhaseFormData: BudgetPhaseFormData): Flow<Resource<ResponseMessage>>

    suspend fun createBudgetPhase(budgetPhase: BudgetPhaseFormData): Flow<Resource<ResponseMessage>>

    fun deleteBudgetPhase(budgetId: String): Flow<Resource<ResponseMessage>>

    fun syncBudgetPhasesToLocal(projectId: String): Flow<Resource<Unit>>
    abstract fun addNewBudgetPhase(newBudgetPhase: BudgetPhaseFormData): Flow<Resource<ResponseMessage>>
}