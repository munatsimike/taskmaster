package com.teqie.taskmaster.domain.useCases.budgetPhase.invoice

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.budget.InvoiceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SyncInvoicesToLocalDbUseCase @Inject constructor(private val invoiceRepository: InvoiceRepository) {
    operator fun invoke(budgetid: String): Flow<Resource<Unit>>{
        return invoiceRepository.syncInvoicesToLocal(budgetid)
    }
}