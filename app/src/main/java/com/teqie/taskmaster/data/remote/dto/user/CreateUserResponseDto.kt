package com.teqie.taskmaster.data.remote.dto.user

data class CreateUserResponseDto(
    val message: String,
    val projectId: String,
    val role: String,
    val userId: String
)