package com.example.taskmaster.data.remote.dto.user

data class UserApiResponseDto(
    val email: String,
    val id: String,
    val isSuperUser: Int,
    val name: String,
    val phone: String,
    val token: String,
    val userDetails: UserDetailsDto
)