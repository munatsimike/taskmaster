package com.teqie.taskmaster.data.remote.dto.orfi

data class ORFIFilesResponseDto(
    val description: String,
    val fileName: String,
    val id: String,
    val isDeleted: Int,
    val orfi_id: String,
    val url: String
)