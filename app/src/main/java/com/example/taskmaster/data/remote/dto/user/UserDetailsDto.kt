package com.example.taskmaster.data.remote.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDetailsDto(
    val address: String?,
    val avatar: String,
    val email: String,
    val id: String,
    val name: String,
    val phone: String
)