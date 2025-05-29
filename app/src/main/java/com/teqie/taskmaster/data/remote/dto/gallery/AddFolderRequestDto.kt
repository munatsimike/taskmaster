package com.teqie.taskmaster.data.remote.dto.gallery

import kotlinx.serialization.Serializable

@Serializable
data class AddFolderRequestDto (
    val name: String,
    val description: String,
    val projectId: String
)