package com.teqie.taskmaster.data.remote.dto.file

import kotlinx.serialization.Serializable

@Serializable
data class AddInvoiceFileResponse(
    val file: InvoiceFileDtoItem,
    val message: String
)