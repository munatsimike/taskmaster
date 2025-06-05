package com.teqie.taskmaster.data.remote.dto.budget

import kotlinx.serialization.Serializable

@Serializable
data class UpdateBudgetPhaseDto(
    val id: String,
    val assignedToName: String,
    val budget: Double,
    val initial_budget: Double,
    val phase: String,
    val user_id: String
)