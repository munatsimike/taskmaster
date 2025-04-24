package com.example.taskmaster.domain.model

data class Totals(
    val resolvedOrfis: Int,
    val totalAmount: Int,
    val totalDuration: Int,
    val totalOrfis: Int,
    val totalPaid: Int,
    val totalScheduleProgress: Double // Ensure this is represented as a percentage or appropriate unit
)
