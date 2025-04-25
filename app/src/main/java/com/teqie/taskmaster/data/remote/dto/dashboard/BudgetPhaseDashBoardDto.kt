package com.teqie.taskmaster.data.remote.dto.dashboard

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// DTO received from the API representing a budget phase in the project dashboard.
@Serializable
data class BudgetPhaseDashBoardDto(
    val id: String,
    val phase: String,
    @SerialName("project_id")
    val projectId: String,
    val totalAmount: Int,
    val totalPaid: Int
)