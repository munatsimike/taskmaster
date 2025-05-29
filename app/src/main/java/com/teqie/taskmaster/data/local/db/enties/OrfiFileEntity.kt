package com.teqie.taskmaster.data.local.db.enties

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "orfi_files")
data class OrfiFileEntity (
    val description: String,
    val fileName: String,
    @PrimaryKey
    val id: String,
    val isDeleted: Int,
    @SerialName("orfi_id")
    val orfiId: String,
    val url: String
)