package com.teqie.taskmaster.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProjectResponseDto(
    val address: String?,
    val createdAt: String,
    val description: String,
    val id: String,
    val isDeleted: Int,
    val name: String,
    val thumbnailUrl: String?,
    val updatedAt: String?
)