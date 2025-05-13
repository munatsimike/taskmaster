package com.teqie.taskmaster.domain.useCases.budgetPhase.invoice

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.budget.InvoiceRepository
import com.teqie.taskmaster.domain.model.budget.invoices.Invoice
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInvoiceUseCase @Inject constructor(private val invoiceRepository: InvoiceRepository) {
    operator fun invoke(budgetId: String): Flow<Resource<List<Invoice>>> {
        return invoiceRepository.fetchInvoices(budgetId)
    }
}