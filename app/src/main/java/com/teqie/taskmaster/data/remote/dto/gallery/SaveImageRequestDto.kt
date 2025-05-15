package com.teqie.taskmaster.data.remote.dto.gallery

data class SaveImageRequestDto (
    val folderId: String,
    val name: String,
    val projectId: String,
    val tags: List<String>,
    val url: String
)