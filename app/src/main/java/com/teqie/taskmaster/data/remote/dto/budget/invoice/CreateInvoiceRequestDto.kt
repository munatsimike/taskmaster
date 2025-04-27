package com.teqie.taskmaster.data.remote.dto.budget.invoice

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateInvoiceRequestDto(
    val amount: Double,
    @SerialName("budget_id")
    val budgetId: String,
    val date: String,
    val paid: Double
)