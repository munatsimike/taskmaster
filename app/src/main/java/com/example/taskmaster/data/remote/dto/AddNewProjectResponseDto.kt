package com.example.taskmaster.data.remote.dto

import com.example.taskmaster.domain.model.APIResponse

data class AddNewProjectResponseDto(
    override val message: String,
    val project: ProjectDto
): APIResponse

