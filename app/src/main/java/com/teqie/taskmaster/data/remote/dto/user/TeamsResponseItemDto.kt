package com.teqie.taskmaster.data.remote.dto.user

data class TeamsResponseItemDto(
    val assigned_at: String?,
    val avatar: String?,
    val email: String?,
    val id: String,
    val isSuperUser: Int = 0,
    val name: String,
    val phone_number: String,
    val role: String,
    val username: String
)