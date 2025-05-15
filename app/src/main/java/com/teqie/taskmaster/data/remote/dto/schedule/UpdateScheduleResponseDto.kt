package com.teqie.taskmaster.data.remote.dto.schedule

data class UpdateScheduleResponseDto (
    val id: String,
    val isDeleted: Boolean?,
    val progress: Float,
    val startdate: String,
    val total_duration: Int
)