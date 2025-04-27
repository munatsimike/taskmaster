package com.teqie.taskmaster.data.mapper.budgetphase.invoice

import com.teqie.taskmaster.data.mapper.DomainToDtoMapper
import com.teqie.taskmaster.data.remote.dto.budget.invoice.CreateInvoiceRequestDto
import com.teqie.taskmaster.domain.model.budget.invoices.CreateInvoiceRequest
import com.teqie.taskmaster.util.DateFormat
import com.teqie.taskmaster.util.isoToReadableDate

object InvoiceToDtoMapper : DomainToDtoMapper<CreateInvoiceRequest, CreateInvoiceRequestDto> {
    override fun CreateInvoiceRequest.toDtoModel(): CreateInvoiceRequestDto {
        return CreateInvoiceRequestDto(
            amount = amount.toDouble(),
            budgetId = budgetId,
            date = date.isoToReadableDate(DateFormat.SIMPLE),
            paid = paid.toDouble()
        )
    }
}