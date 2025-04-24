package com.example.taskmaster.data.remote.dto.budget

import com.example.taskmaster.data.remote.dto.schedule.ScheduleResponseDto
import com.example.taskmaster.domain.model.RemoteResponse

data class CreateBudgetPhaseResponse(
    override val message: String,
    val phaseResponse: PhaseResponse,
    val scheduleResponse: ScheduleResponseDto
): RemoteResponse