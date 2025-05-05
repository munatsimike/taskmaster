package com.teqie.taskmaster.data.mapper.user

import com.teqie.taskmaster.data.local.db.enties.TeamMemberEntity
import com.teqie.taskmaster.data.mapper.DtoToEntityMapper
import com.teqie.taskmaster.data.remote.dto.user.TeamsResponseItemDto

object TeamsDtoToDomainMapper: DtoToEntityMapper<TeamsResponseItemDto, TeamMemberEntity> {
    override fun TeamsResponseItemDto.toEntity(projectId: String?): TeamMemberEntity {
        TODO("Not yet implemented")
    }
}