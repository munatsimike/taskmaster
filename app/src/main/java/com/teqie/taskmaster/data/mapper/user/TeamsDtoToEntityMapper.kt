package com.teqie.taskmaster.data.mapper.user

import com.teqie.taskmaster.data.local.db.enties.TeamMemberEntity
import com.teqie.taskmaster.data.mapper.DtoToEntityMapper
import com.teqie.taskmaster.data.remote.dto.user.TeamsResponseItemDto

object TeamsDtoToEntityMapper: DtoToEntityMapper<TeamsResponseItemDto, TeamMemberEntity> {
    override fun TeamsResponseItemDto.toEntity(projectId: String?): TeamMemberEntity {
       return TeamMemberEntity(
           id = id,
           assignedAt = assignedAt,
           avatarUrl = avatar,
           email = email,
           isSuperUser = isSuperUser,
           name = name,
           phoneNumber = phoneNumber,
           role = role,
           username = username
       )
    }
}