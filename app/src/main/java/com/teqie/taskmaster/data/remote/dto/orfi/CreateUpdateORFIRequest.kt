package com.teqie.taskmaster.data.remote.dto.orfi

import kotlinx.serialization.Serializable

@Serializable
data class CreateUpdateORFIRequest (
    val assignedTo: String?,
    val dueDate: String,
    val projectId: String,
    val question: String,
    val resolved: Boolean
)