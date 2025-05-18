package com.teqie.taskmaster.domain.gallery

data class Folder (
    val description: String?,
    val id: String,
    val isDeleted: Boolean,
    val name: String,
    val projectId: String
)