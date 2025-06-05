package com.teqie.taskmaster.data.local.db.enties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget_phase")
class BudgetPhaseEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val phase: String,
    val projectId: String,
    val totalAmount: Int,
    val totalPaid: Int,
    val userId: String?,
    val assignedToUsername: String?,
    val assignedToName: String?,
    val assignedToAvatar: String?,
    val initialBudget: Double,
    val budget: Double
)
