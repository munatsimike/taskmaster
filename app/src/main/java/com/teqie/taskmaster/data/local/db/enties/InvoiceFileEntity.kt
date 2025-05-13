package com.teqie.taskmaster.data.local.db.enties

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity
class InvoiceFileEntity(
    val createdAt: String,
    val description: String,
    val fileName: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @SerialName("invoice_id")
    var invoiceId: String,
    val isDeleted: Int,
    val updatedAt: String,
    val url: String
)