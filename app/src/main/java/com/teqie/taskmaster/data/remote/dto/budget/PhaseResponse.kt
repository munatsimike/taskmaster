package com.teqie.taskmaster.data.remote.dto.budget

data class PhaseResponse(
    val budget: Int,
    val id: Int,
    val initial_budget: Int,
    val isDeleted: Boolean,
    val phase: String,
    val project_id: String,
    val user_id: String)