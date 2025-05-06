package com.teqie.taskmaster.data.mapper.user

import com.teqie.taskmaster.data.local.db.enties.TeamMemberEntity
import com.teqie.taskmaster.data.mapper.EntityToDomain
import com.teqie.taskmaster.domain.model.teamMember.Role
import com.teqie.taskmaster.domain.model.teamMember.TeamMember

object TeamsEntityToDomainMapper: EntityToDomain<TeamMemberEntity, TeamMember> {
    override fun TeamMemberEntity.toDomainModel(): TeamMember {
       return TeamMember(
           assignedAt = assignedAt,
           avatarUrl = avatarUrl,
           email = email,
           id = id,
           isSuperUser = isSuperUser == 0,
           name = name,
           phoneNumber = phoneNumber,
           role = Role.valueOf(role),
           username = username
       )
    }
}