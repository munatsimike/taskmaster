package com.example.taskmaster.data.mapper

import com.example.taskmaster.data.remote.dto.dashboard.TotalsResponseDto
import com.example.taskmaster.domain.model.Totals

object CommonMapper {

    fun TotalsResponseDto.toTotalDomain(): Totals {
        return Totals(
            resolvedOrfis = resolvedOrfis,
            totalAmount = totalAmount,
            totalDuration = totalDuration,
            totalOrfis = totalOrfis,
            totalPaid = totalPaid,
            totalScheduleProgress = totalScheduleProgress
        )
    }
}