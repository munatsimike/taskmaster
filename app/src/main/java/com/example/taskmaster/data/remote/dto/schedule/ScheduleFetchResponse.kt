package com.example.taskmaster.data.remote.dto.schedule

data class ScheduleFetchResponse(
    val phase: String,
    val project_id: String,
    override val id: String,
    override val isDeleted: Boolean?,
    override val progress: Float,
    override val startdate: String,
    override val total_duration: Int
) : ScheduleDto(id, isDeleted, progress,  startdate, total_duration)