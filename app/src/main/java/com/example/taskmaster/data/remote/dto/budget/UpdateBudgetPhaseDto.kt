package com.example.taskmaster.data.remote.dto.budget

data class UpdateBudgetPhaseDto(
    val id: String,
    val assignedToName: String,
    val budget: Double,
    val initial_budget: Double,
    val phase: String,
    val user_id: String
)