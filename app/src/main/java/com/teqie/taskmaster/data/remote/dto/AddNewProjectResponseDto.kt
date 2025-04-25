package com.teqie.taskmaster.data.remote.dto

import com.teqie.taskmaster.domain.model.RemoteResponse

data class AddNewProjectResponseDto(
    override val message: String,
    val project: ProjectResponseDto
): RemoteResponse

