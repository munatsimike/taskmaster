package com.teqie.taskmaster.data.remote.dto.gallery

data class AddFolderRequestDto (
    val name: String,
    val description: String,
    val projectId: String
)