package com.teqie.taskmaster.data.remote.dto.budget

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BudgetPhaseResponseDto(
    val id: String,
    val phase: String,
    @SerialName("project_id")
    val projectId: String?,
    val totalAmount: Int,
    val totalPaid: Int,
    @SerialName("user_id")
    val userId: String?,
    val assignedToUsername: String?,
    val assignedToName: String?,
    val assignedToAvatar: String?,
    @SerialName("initial_budget")
    val initialBudget: Double,
    val budget: Double
)