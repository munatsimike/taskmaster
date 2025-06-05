package com.teqie.taskmaster.data.remote.dto.budget

import kotlinx.serialization.Serializable

@Serializable
data class CreateBudgetPhaseDto(
    val progress: Double,
    val total_duration: Int,
    val startdate: String,
    val budget: String,
    val user_id: String,
    val phase: String,
    val project_id: String,
)