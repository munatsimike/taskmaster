package com.teqie.taskmaster.data.remote.dto.budget

import kotlinx.serialization.Serializable

@Serializable
data class UpdateBudgetPhaseResponseDto (val phase: String, val message: String)