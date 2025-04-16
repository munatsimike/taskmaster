package com.example.taskmaster.data.remote.dto.budget

abstract class BaseBudgetPhaseDto(
    open val phase: String,
    open val project_id: String,
    open val id: String,
    open  val totalAmount: Int,
    open val totalPaid: Int,
)
