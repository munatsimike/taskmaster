package com.teqie.taskmaster.data.mapper.user

import com.teqie.taskmaster.data.local.db.enties.TeamMemberEntity
import com.teqie.taskmaster.data.mapper.EntityToDomain
import com.teqie.taskmaster.domain.model.teamMember.TeamMember

object TeamsEntityToDomainMapper: EntityToDomain<TeamMemberEntity, TeamMember> {
    override fun TeamMemberEntity.toDomainModel(): TeamMember {
        TODO("Not yet implemented")
    }
}