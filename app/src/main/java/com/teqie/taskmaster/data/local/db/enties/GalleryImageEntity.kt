package com.teqie.taskmaster.data.local.db.enties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
class GalleryImageEntity (
    val createdAt: String,
    val description: String?,
    val folderId: String,
    @PrimaryKey
    val id: String,
    val imageName: String,
    val imageUrl: String,
    val isDeleted: Int,
    val projectId: String,
    val tags: List<String>,
    val updatedAt: String
    )