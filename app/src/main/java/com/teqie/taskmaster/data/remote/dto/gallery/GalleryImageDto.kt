package com.teqie.taskmaster.data.remote.dto.gallery

data class GalleryImageDto (
    val createdAt: String,
    val description: String?,
    val folderId: Int,
    val id: String,
    val imageName: String,
    val imageUrl: String,
    val isDeleted: Int,
    val projectId: String,
    val tags: List<String>,
    val updatedAt: String
)