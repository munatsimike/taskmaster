package com.example.taskmaster.domain.model.teamMember

data class TeamMember(
    val assignedAt: String,
    val avatarUrl: String?=null,
    val email: String?,
    val id: String,
    val isSuperUser: Boolean,
    val name: String,
    val phoneNumber: String?,
    val role: Role,
    val username: String?
)