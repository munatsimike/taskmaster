package com.teqie.taskmaster.data.mapper.project

import com.teqie.taskmaster.data.local.db.enties.ProjectEntity
import com.teqie.taskmaster.data.mapper.DtoToEntityMapper
import com.teqie.taskmaster.data.remote.dto.ProjectResponseDto

object ProjectDtoToEntityMapper : DtoToEntityMapper<ProjectResponseDto, ProjectEntity> {
    override fun ProjectResponseDto.toEntity(projectId:String?): ProjectEntity {
        return ProjectEntity(
            id = id,
            address = address,
            createdAt = createdAt,
            description = description,
            isDeleted = isDeleted,
            name = name,
            thumbnailUrl = thumbnailUrl,
            updatedAt = updatedAt
        )
    }
}