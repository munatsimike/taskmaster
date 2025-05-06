package com.teqie.taskmaster.domain.useCases.budgetPhase.invoice

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.buget.InvoiceRepository
import com.teqie.taskmaster.ui.screen.bugdetPhase.InvoiceFile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInvoiceFilesUseCase @Inject constructor(private val invoiceRepository: InvoiceRepository) {
    operator fun invoke(invoiceId: String): Flow<Resource<List<InvoiceFile>>> {
        return invoiceRepository.geInvoiceFile(invoiceId)
    }
}