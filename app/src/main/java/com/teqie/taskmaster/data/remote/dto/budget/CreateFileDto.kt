package com.teqie.taskmaster.data.remote.dto.budget

import kotlinx.serialization.Serializable

@Serializable
data class CreateFileDto(
    val description: String,
    val fileName: String,
    val orfi_id: String,
    val invoice_id: String,
    val url: String
)