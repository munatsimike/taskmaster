package com.teqie.taskmaster.data.remote.dto.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// DTO representing a schedule item received from the API.
// This is mapped to a Room Entity
@Serializable
data class ScheduleResponseDto(
    val phase: String,
    @SerialName("project_id")
    val projectId: String,
    val id: String, val isDeleted: Boolean?,
    val progress: Float,
    val startdate: String,
    val total_duration: Int
)