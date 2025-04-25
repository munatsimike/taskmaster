package com.teqie.taskmaster.data.remote.dto.budget

import com.teqie.taskmaster.data.remote.dto.budget.invoice.file.InvoiceFileDto
import com.teqie.taskmaster.domain.model.RemoteResponse

data class CreateInvoiceFileResponse(
    val file: InvoiceFileDto,
    override val message: String
): RemoteResponse