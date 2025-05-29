package com.teqie.taskmaster.data.remote.dto.gallery

import kotlinx.serialization.Serializable

@Serializable
class FoldersResponseDto (
    val description: String?,
    val id: String,
    val isDeleted: Int,
    val name: String,
    val projectId: String
)