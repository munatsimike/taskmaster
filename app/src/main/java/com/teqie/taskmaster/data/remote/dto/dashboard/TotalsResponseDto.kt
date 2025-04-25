package com.teqie.taskmaster.data.remote.dto.dashboard

import kotlinx.serialization.Serializable

@Serializable
data class TotalsResponseDto(
    val resolvedOrfis: Int,
    val totalAmount: Int,
    val totalDuration: Int,
    val totalOrfis: Int,
    val totalPaid: Int,
    val totalScheduleProgress: Double
)