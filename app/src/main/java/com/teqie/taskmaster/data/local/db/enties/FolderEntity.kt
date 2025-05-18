package com.teqie.taskmaster.data.local.db.enties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folders")
class FolderEntity (
    val description: String?,
    @PrimaryKey
    val id: String,
    val isDeleted: Boolean,
    val name: String,
    val projectId: String
)
