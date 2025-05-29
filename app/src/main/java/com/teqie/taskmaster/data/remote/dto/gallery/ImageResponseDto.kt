package com.teqie.taskmaster.data.remote.dto.gallery

import kotlinx.serialization.Serializable

@Serializable
data class ImageResponseDto (
    val currentPage: Int,
    val images: List<GalleryImageDto>,
    val totalItems: Int,
    val totalPages: Int
)
