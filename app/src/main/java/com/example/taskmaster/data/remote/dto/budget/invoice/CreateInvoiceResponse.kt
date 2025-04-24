package com.example.taskmaster.data.remote.dto.budget.invoice

import com.example.taskmaster.domain.model.RemoteResponse

data class CreateInvoiceResponse(
    val invoice: InvoiceResponseDto,
    override val message: String
): RemoteResponse