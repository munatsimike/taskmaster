package com.example.taskmaster.data.remote.dto.project

data class CreateProjectRequestDto(
    val thumbnailUrl: String?,
    override val name: String,
    override val description: String
): ProjectRequestDto