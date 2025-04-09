package com.example.taskmaster.data.remote.dto.project.schedule

open class ScheduleDto(
    open val id: String,
    open val isDeleted: Boolean?,
    open val progress: Float,
    open val startdate: String,
    open val total_duration: Int
)