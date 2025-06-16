package com.teqie.taskmaster.domain.gallery

import java.time.LocalDate


class GalleryImage(
    val createdAt: LocalDate?,
    val description: String?,
    val folderId: String,
    val id: String,
    val imageName: String,
    val imageUrl: String,
    val isDeleted: Boolean,
    val projectId: String,
    val tags: List<String>,
    val updatedAt: LocalDate?
    )