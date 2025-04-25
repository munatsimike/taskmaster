package com.teqie.taskmaster.data.local.db.enties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
class ProjectEntity (
    @PrimaryKey
    val id: String,
    val address: String?,
    val createdAt: String,
    val description: String,
    val isDeleted: Int,
    val name: String,
    val thumbnailUrl: String?,
    val updatedAt: String?
)