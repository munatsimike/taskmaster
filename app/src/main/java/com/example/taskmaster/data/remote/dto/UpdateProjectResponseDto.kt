package com.example.taskmaster.data.remote.dto

import com.example.taskmaster.domain.model.APIResponse
import com.squareup.moshi.Json

data class UpdateProjectResponseDto(
    val description: String,
    val id: String,
    val name: String,
    val updated_at: String,
    @Json(ignore = true) override val message: String = "Project updated successfully",
): APIResponse