package com.teqie.taskmaster.data.remote.dto.budget.invoice.file

data class InvoiceFileResponseDto(
    val createdAt: String,
    val description: String,
    val fileName: String,
    val id: String,
    var invoice_id: String,
    val isDeleted: Int,
    val updatedAt: String,
    val url: String
)
