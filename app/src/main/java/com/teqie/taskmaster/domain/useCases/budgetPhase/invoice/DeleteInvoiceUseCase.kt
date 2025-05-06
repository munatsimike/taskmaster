package com.teqie.taskmaster.domain.useCases.budgetPhase.invoice

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.buget.InvoiceRepository
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteInvoiceUseCase @Inject constructor(private val invoiceRepository: InvoiceRepository){
    operator fun invoke(invoiceId: String): Flow<Resource<ResponseMessage>> {
        return invoiceRepository.deleteInvoice(invoiceId)
    }
}