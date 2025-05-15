package com.teqie.taskmaster.data.local.db.enties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule")
data class ScheduleEntity(
    @PrimaryKey
    val id: String,
    val isDeleted: Boolean?,
    val phase: String,
    val progress: Float,
    val projectId: String,
    val startDate: String,
    val totalDuration: Int   //
)