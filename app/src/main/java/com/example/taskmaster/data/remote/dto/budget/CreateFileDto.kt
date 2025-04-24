package com.example.taskflow.data.remote.dto.project.post.budget

data class CreateFileDto(
    val description: String,
    val fileName: String,
    val orfi_id: String,
    val invoice_id: String,
    val url: String
)