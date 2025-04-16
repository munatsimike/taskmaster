package com.example.taskmaster.data.remote.dto

data class ProjectDto(
    val address: String?,
    val createdAt: String,
    val description: String,
    val id: String,
    val isDeleted: Any,
    val name: String,
    val thumbnailUrl: String?,
    val updatedAt: String?
)