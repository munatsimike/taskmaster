package com.example.taskmaster.data.remote.dto.schedule

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateScheduleRequestDto(
    val id: String,
    val isDeleted: Boolean?,
    val progress: Float,
    @SerialName("startdate")
    val startDate: String,
    @SerialName("total_duration")
    val total_duration: Int
)