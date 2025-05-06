package com.teqie.taskmaster.domain.model.teamMember

data class AssignedTeamMember(
    override val userId: String?,
    override val assignedToUsername: String?,
    override val assignedToName: String?,
    override val assignedToAvatar: String?,
) : BaseTeamMember(userId, assignedToUsername, assignedToName, assignedToAvatar)