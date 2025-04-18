package com.example.taskmaster.data.local.db.enties

import androidx.room.Entity

@Entity(tableName = "projects")
class ProjectEntity (
    val address: String?,
    val createdAt: String,
    val description: String,
    val id: String,
    val isDeleted: Any,
    val name: String,
    val thumbnailUrl: String?,
    val updatedAt: String?
)