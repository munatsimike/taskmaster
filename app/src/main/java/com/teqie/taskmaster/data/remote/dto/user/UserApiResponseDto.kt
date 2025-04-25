package com.teqie.taskmaster.data.remote.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserApiResponseDto(
    val email: String,
    val id: String,
    val isSuperUser: Int,
    val name: String,
    val phone: String,
    val token: String,
    val userDetails: UserDetailsDto
)