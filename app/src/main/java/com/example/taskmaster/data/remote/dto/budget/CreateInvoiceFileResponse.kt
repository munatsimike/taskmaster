package com.example.taskmaster.data.remote.dto.budget

import com.example.taskmaster.data.remote.dto.budget.invoice.file.InvoiceFileDto
import com.example.taskmaster.domain.model.RemoteResponse

data class CreateInvoiceFileResponse(
    val file: InvoiceFileDto,
    override val message: String
): RemoteResponse