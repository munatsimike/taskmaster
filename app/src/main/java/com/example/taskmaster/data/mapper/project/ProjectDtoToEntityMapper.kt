package com.example.taskmaster.data.mapper.project

import com.example.taskmaster.data.local.db.enties.ProjectEntity
import com.example.taskmaster.data.mapper.DtoToEntityMapper
import com.example.taskmaster.data.remote.dto.ProjectResponseDto

object ProjectDtoToEntityMapper : DtoToEntityMapper<ProjectResponseDto, ProjectEntity> {
    override fun ProjectResponseDto.toEntity(): ProjectEntity {
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