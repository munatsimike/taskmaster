package com.teqie.taskmaster.data.remote.dto.file

import kotlinx.serialization.Serializable

@Serializable
data class UpdateFileRequestDTo(
    val id: String,
    val fileName: String,
    val description: String
)
