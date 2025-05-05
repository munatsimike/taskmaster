package com.teqie.taskmaster.domain.useCases.budgetPhase.invoice


import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.buget.BudgetPhaseRepository
import com.teqie.taskmaster.domain.buget.InvoiceRepository
import com.teqie.taskmaster.domain.model.budget.invoices.CreateInvoiceRequest
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateBudgetInvoiceUseCase @Inject constructor(private val invoiceRepository: InvoiceRepository) {
    suspend operator fun invoke(invoiceUpdateRequest: CreateInvoiceRequest): Flow<Resource<ResponseMessage>> {
       return invoiceRepository.updateInvoice(invoiceUpdateRequest)
    }
}