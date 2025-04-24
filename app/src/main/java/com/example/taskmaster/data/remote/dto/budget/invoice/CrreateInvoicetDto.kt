package com.example.taskmaster.data.remote.dto.budget.invoice

data class CrreateInvoicetDto(
    val amount: Double,
    val budget_id: String,
    val date: String,
    val paid: Double
)