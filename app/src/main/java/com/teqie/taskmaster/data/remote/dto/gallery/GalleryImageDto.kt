package com.teqie.taskmaster.data.remote.dto.gallery

import kotlinx.serialization.Serializable

@Serializable
data class GalleryImageDto (
    val createdAt: String,
    val description: String?,
    val folderId: String,
    val id: String,
    val imageName: String,
    val imageUrl: String,
    val isDeleted: Int,
    val projectId: String,
    val tags: List<String>,
    val updatedAt: String
)