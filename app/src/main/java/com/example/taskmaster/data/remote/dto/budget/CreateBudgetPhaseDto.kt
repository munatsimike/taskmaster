package com.example.taskflow.data.remote.dto.project.post.budget

data class CreateBudgetPhaseDto(
    val progress: Double,
    val total_duration: Int,
    val startdate: String,
    val budget: String,
    val user_id: String,
    val phase: String,
    val project_id: String,
)