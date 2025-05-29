package com.teqie.taskmaster.data.remote.dto.gallery

import kotlinx.serialization.Serializable

@Serializable
data class SaveImageRequestDto (
    val folderId: String,
    val name: String,
    val projectId: String,
    val tags: List<String>,
    val url: String
)