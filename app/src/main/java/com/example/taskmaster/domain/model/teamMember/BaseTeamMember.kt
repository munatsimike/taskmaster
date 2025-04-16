package com.example.taskmaster.domain.model.teamMember

open class BaseTeamMember (
    open val userId: String,
    open val assignedToUsername: String,
    open val assignedToName: String,
    open val assignedToAvatar: String?,
)