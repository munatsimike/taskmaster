package com.example.taskmaster.data.remote.dto.project.orfi

data class OrfiDto(
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