package com.teqie.taskmaster.data.mapper

import com.teqie.taskmaster.data.remote.dto.dashboard.TotalsResponseDto
import com.teqie.taskmaster.domain.model.Totals

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