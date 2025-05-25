package com.teqie.taskmaster.data.local.db.enties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orfi_files")
class OrfiFileEntity (
    val description: String,
    val fileName: String,
    @PrimaryKey
    val id: String,
    val isDeleted: Int,
    val orfi_id: String,
    val url: String
)