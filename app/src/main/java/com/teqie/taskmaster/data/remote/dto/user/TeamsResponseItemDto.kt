package com.teqie.taskmaster.data.remote.dto.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamsResponseItemDto(
    @SerialName("assigned_at")
    val assignedAt: String?,
    val avatar: String?,
    val email: String?,
    val id: String,
    val isSuperUser: Int = 0,
    val name: String,
    @SerialName("phone_number")
    val phoneNumber: String,
    val role: String,
    val username: String
)