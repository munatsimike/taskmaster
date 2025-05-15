package com.teqie.taskmaster.data.remote.dto.schedule

data class ScheduleFetchResponse(
    val phase: String,
    val project_id: String,
    val id: String,
    val isDeleted: Boolean?,
    val progress: Float,
    val startdate: String,
    val total_duration: Int
)