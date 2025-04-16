package com.example.taskmaster.data.remote.dto.dashboard

data class TotalsDto(
    val resolvedOrfis: Int,
    val totalAmount: Int,
    val totalDuration: Int,
    val totalOrfis: Int,
    val totalPaid: Int,
    val totalScheduleProgress: Double
)