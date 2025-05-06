package com.teqie.taskmaster.domain.buget

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.budget.invoices.CreateInvoiceRequest
import com.teqie.taskmaster.ui.model.ResponseMessage
import com.teqie.taskmaster.ui.screen.bugdetPhase.InvoiceFile
import kotlinx.coroutines.flow.Flow

interface InvoiceRepository {
    fun syncInvoicesToLocal(budgetId: String): Flow<Resource<Unit>>

    fun createBudgetInvoice(createInvoiceRequest: CreateInvoiceRequest): Flow<Resource<ResponseMessage>>

    fun deleteInvoice(invoiceId: String): Flow<Resource<ResponseMessage>>

    suspend fun updateInvoice(invoiceUpdateRequest: CreateInvoiceRequest): Flow<Resource<ResponseMessage>>

    fun geInvoiceFile(invoiceId: String): Flow<Resource<List<InvoiceFile>>>

    fun updateInvoiceFile(invoiceFile: InvoiceFile): Flow<Resource<ResponseMessage>>
}