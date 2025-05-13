package com.teqie.taskmaster.data.remote.dto.file

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvoiceFileDtoItem(
    val createdAt: String,
    val description: String,
    val fileName: String,
    val id: String,
    @SerialName("invoice_id")
    var invoiceId: String,
    val isDeleted: Int,
    val updatedAt: String,
    val url: String
)
