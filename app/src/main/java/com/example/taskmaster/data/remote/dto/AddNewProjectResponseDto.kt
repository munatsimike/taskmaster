package com.example.taskmaster.data.remote.dto

import com.example.taskmaster.domain.model.RemoteResponse

data class AddNewProjectResponseDto(
    override val message: String,
    val project: ProjectResponseDto
): RemoteResponse

