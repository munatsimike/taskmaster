package com.teqie.taskmaster.data.remote.dto.budget.invoice

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvoiceResponseDto(
    val amount: Double,
    val assignedTo: Int,
    val assignedToAvatar: String? = null,
    val assignedToName: String? = null,
    val assignedToUsername: String,
    @SerialName("budget_id")
    val budgetId: String,
    val date: String,
    val id: String,
    val isDeleted: Int,
    val paid: Double
)