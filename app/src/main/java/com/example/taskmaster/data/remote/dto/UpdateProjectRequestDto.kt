package com.example.taskmaster.data.remote.dto


data class UpdateProjectRequestDto(
    val id: String,
    val thumbnailUrl: String?,
    override val name: String,
    override val description: String
): ProjectRequestDto