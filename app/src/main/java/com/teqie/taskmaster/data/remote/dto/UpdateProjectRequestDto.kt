package com.teqie.taskmaster.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProjectRequestDto(
    val id: String,
    val thumbnailUrl: String?,
    override val name: String,
    override val description: String
): ProjectRequestDto