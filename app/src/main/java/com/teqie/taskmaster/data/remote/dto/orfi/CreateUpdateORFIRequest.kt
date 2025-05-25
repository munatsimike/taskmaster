package com.teqie.taskmaster.data.remote.dto.orfi

data class CreateUpdateORFIRequest (
    val assignedTo: String?,
    val dueDate: String,
    val projectId: String,
    val question: String,
    val resolved: Boolean
)