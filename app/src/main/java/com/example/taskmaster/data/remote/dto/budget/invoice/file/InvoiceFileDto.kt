package com.example.taskmaster.data.remote.dto.budget.invoice.file

data class InvoiceFileDto(
    val createdAt: String,
    val description: String,
    val fileName: String,
    val id: String,
    var invoice_id: String,
    val isDeleted: Int,
    val updatedAt: String,
    val url: String
)
