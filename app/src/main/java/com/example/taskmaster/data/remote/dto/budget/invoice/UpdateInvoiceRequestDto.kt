package com.example.taskmaster.data.remote.dto.budget.invoice

data class UpdateInvoiceRequestDto(
    val budget_id: String,
    val assignedTo: String,
    val date: String,
    val amount: Double,
    val paid: Double
)
