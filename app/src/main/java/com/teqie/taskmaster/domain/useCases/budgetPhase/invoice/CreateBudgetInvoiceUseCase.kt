package com.teqie.taskmaster.domain.useCases.budgetPhase.invoice


import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.budget.InvoiceRepository
import com.teqie.taskmaster.domain.model.budget.invoices.CreateInvoiceRequest
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateBudgetInvoiceUseCase @Inject constructor(private val invoiceRepository: InvoiceRepository) {
    operator fun invoke(invoiceRequest: CreateInvoiceRequest): Flow<Resource<ResponseMessage>> {
       return invoiceRepository.createBudgetInvoice(invoiceRequest)
    }
}