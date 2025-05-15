package com.teqie.taskmaster.data.remote.dto.gallery

data class ImageResponseDto (
    val currentPage: Int,
    val images: List<GalleryImageDto>,
    val totalItems: Int,
    val totalPages: Int
)
