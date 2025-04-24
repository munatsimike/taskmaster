package com.example.taskmaster.data.remote.dto.orfi

import kotlinx.serialization.Serializable

@Serializable
data class OrfiResponseDto(
    val assignedAvatar: String? = null,
    val assignedName: String? = null,
    val assignedTo: String?= null ,
    val assignedUserName: String?= null ,
    val createdAt: String = "",
    val dueDate: String = "",
    val id: String= "" ,
    val isDeleted: Int,
    val projectId: String = "",
    val question: String = "",
    val resolved: Int,
    val updatedAt: String = ""
)