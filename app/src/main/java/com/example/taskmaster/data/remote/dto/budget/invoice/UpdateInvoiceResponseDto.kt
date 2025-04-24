package com.example.taskmaster.data.remote.dto.budget.invoice

import com.example.taskmaster.domain.model.RemoteResponse

class UpdateInvoiceResponseDto(
    val invoice: InvoiceResponseDto,
    override val message: String
): RemoteResponse