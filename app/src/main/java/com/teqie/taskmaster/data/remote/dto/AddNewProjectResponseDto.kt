package com.teqie.taskmaster.data.remote.dto

import com.teqie.taskmaster.domain.model.RemoteResponse
import kotlinx.serialization.Serializable

@Serializable
data class AddNewProjectResponseDto(
    override val message: String,
    val project: ProjectResponseDto
): RemoteResponse

