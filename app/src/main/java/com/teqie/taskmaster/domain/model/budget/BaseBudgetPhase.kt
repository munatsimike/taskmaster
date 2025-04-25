package com.teqie.taskmaster.domain.model.budget

abstract class BaseBudgetPhase(
    open val phase: String,
    open val projectId: String,
    open val id: String,
    open val totalAmount: Int,
    open val totalPaid: Int
)