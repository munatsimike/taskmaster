package com.example.taskmaster.data.remote.dto.budget.invoice

data class InvoiceResponseDto(
    val amount: Double,
    val assignedTo: Int,
    val assignedToAvatar: String? = null,
    val assignedToName: String? = null,
    val assignedToUsername: String,
    val budget_id: String,
    val date: String,
    val id: String,
    val isDeleted: Int,
    val paid: Double
)