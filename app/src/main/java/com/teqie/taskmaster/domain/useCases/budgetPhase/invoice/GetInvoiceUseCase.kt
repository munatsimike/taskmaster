package com.teqie.taskmaster.domain.useCases.budgetPhase.invoice

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.buget.BudgetPhaseRepository
import com.teqie.taskmaster.domain.buget.InvoiceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInvoiceUseCase @Inject constructor(private val invoiceRepository: InvoiceRepository) {
    operator fun invoke(budgetId: String): Flow<Resource<Unit>> {
        return invoiceRepository.syncInvoicesToLocal(budgetId)
    }
}