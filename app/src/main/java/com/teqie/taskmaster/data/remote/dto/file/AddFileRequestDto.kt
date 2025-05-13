package com.teqie.taskmaster.data.remote.dto.file

data class AddFileRequestDto(
    val description: String,
    val fileName: String,
    val orfi_id: String,
    val invoice_id: String,
    val url: String
)