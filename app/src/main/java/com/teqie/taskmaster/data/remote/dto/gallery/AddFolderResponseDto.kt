package com.teqie.taskmaster.data.remote.dto.gallery

import kotlinx.serialization.Serializable

@Serializable
data class AddFolderResponseDto (
    val description: String,
    val id: String,
    val isDeleted: Boolean,
    val name: String,
    val projectId: String
)