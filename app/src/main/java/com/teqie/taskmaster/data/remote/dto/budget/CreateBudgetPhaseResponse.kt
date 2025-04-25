package com.teqie.taskmaster.data.remote.dto.budget

import com.teqie.taskmaster.data.remote.dto.schedule.ScheduleResponseDto
import com.teqie.taskmaster.domain.model.RemoteResponse

data class CreateBudgetPhaseResponse(
    override val message: String,
    val phaseResponse: PhaseResponse,
    val scheduleResponse: ScheduleResponseDto
): RemoteResponse