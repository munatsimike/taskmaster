package com.teqie.taskmaster.data.remote.dto

import com.squareup.moshi.Json
import com.teqie.taskmaster.domain.model.RemoteResponse

data class UpdateProjectResponseDto(
    val description: String,
    val id: String,
    val name: String,
    val updated_at: String,
    @Json(ignore = true) override val message: String = "Project updated successfully",
): RemoteResponse