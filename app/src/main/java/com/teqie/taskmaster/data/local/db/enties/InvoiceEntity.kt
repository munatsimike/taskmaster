package com.teqie.taskmaster.data.local.db.enties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "invoice")
class InvoiceEntity(
    @PrimaryKey
    val id: String,
    val amount: Double,
    val assignedTo: Int,
    val assignedToAvatar: String? = null,
    val assignedToName: String? = null,
    val assignedToUsername: String,
    val budgetId: String,
    val date: String,
    val isDeleted: Int,
    val paid: Double
)