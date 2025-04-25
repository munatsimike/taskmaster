package com.teqie.taskmaster.data.remote.dto.budget

data class BudgetPhaseResponseDto(
    val id: String,
    val phase: String,
    val project_id: String,
    val totalAmount: Int,
    val totalPaid: Int,
    val user_id: String,
    val assignedToUsername: String,
    val assignedToName: String,
    val assignedToAvatar: String?,
    val initial_budget: Double,
    val budget: Double
)