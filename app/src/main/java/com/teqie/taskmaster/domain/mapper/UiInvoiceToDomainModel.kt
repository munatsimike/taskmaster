package com.teqie.taskmaster.domain.mapper

import com.teqie.taskmaster.domain.model.budget.invoices.CreateInvoiceRequest
import com.teqie.taskmaster.domain.model.budget.invoices.Invoice

object UiInvoiceToDomainModel : UiToDomainModel<Invoice, CreateInvoiceRequest> {

    override fun Invoice.toDomainModel(): CreateInvoiceRequest {
        return CreateInvoiceRequest(
            id = id,
            budgetId = this.budgetId,
            date = this.date,
            amount = this.amount.toString(),
            paid = this.paid.toString()
        )
    }
}
