package com.example.taskflow.data.remote.dto.project.post.orfi

data class CreateEditOrfiFileResponse(
    val description: String,
    val fileName: String,
    val id: String,
    val isDeleted: Int,
    val orfi_id: String,
    val url: String
)