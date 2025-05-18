package com.teqie.taskmaster.data.local.db.enties

import androidx.room.Entity
import com.teqie.taskmaster.data.remote.dto.gallery.GalleryImageDto

@Entity(tableName = "images")
class GalleryImageEntity (
    val currentPage: Int,
    val images: List<GalleryImageDto>,
    val totalItems: Int,
    val totalPages: Int
    )