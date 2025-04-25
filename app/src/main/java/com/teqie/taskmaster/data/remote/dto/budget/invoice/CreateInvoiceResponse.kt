package com.teqie.taskmaster.data.remote.dto.budget.invoice

import com.teqie.taskmaster.domain.model.RemoteResponse

data class CreateInvoiceResponse(
    val invoice: InvoiceResponseDto,
    override val message: String
): RemoteResponse