package com.teqie.taskmaster.domain

import com.teqie.taskmaster.domain.model.budget.BudgetPhase
import com.teqie.taskmaster.domain.model.budget.BudgetPhaseFormData
import com.teqie.taskmaster.domain.model.budget.invoices.CreateInvoiceRequest
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow

interface BudgetPhaseRepository {

    fun fetchBudgetPhases(projectId: String): Flow<Resource<List<BudgetPhase>>>

    suspend fun updateBudgetPhase(budgetPhaseFormData: BudgetPhaseFormData): Flow<Resource<ResponseMessage>>

    suspend fun createBudgetPhase(budgetPhase: BudgetPhaseFormData): Flow<Resource<ResponseMessage>>

    suspend fun deleteBudgetPhase(budgetId: String): Flow<Resource<ResponseMessage>>

    fun syncBudgetPhasesToLocal(projectId: String): Flow<Resource<Unit>>

    fun syncInvoicesToLocal(budgetId: String): Flow<Resource<Unit>>

    fun createBudgetInvoice(createInvoiceRequest: CreateInvoiceRequest): Flow<Resource<ResponseMessage>>

    fun deleteInvoice(invoiceId: String): Flow<Resource<ResponseMessage>>

    suspend fun updateInvoice(invoiceUpdateRequest: CreateInvoiceRequest): Flow<Resource<ResponseMessage>>

}