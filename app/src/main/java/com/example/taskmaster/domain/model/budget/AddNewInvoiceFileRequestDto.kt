package com.example.taskflow.domain.model.budget

data class AddInvoiceFileRequest(
    val description: String = "",
    val fileName: String = "",
    val invoiceId: String = "",
    val isDeleted: Boolean = false,
    val url: String = ""
)