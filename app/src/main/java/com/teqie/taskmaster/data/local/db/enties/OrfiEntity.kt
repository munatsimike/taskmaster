package com.teqie.taskmaster.data.local.db.enties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orfi")
class OrfiEntity (
    val assignedAvatar: String?,  // Depending on use case, you might want to handle avatar differently
    val assignedName: String?,
    val assignedTo: String?,
    val assignedUserName: String?,
    val createdAt: String,  // Consider using a Date type if appropriate
    val dueDate: String,    // Consider using a Date type if appropriate
    @PrimaryKey
    val id: String,
    val isDeleted: Boolean, //
    val projectId: String,
    val question: String,
    val resolved: Boolean,  // Changed to Boolean for clarity
    val updatedAt: String
)