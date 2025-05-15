package com.teqie.taskmaster.data.remote.dto.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleFetchResponse(
    val phase: String,
    @SerialName("project_id")
    val projectId: String,
    val id: String,
    val isDeleted: Boolean?,
    val progress: Float,
    val startdate: String,
    @SerialName("total_duration")
    val totalDuration: Int
)