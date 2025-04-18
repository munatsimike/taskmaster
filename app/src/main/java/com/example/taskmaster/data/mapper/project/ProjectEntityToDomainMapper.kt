package com.example.taskmaster.data.mapper.project

import com.example.taskmaster.data.local.db.enties.ProjectEntity
import com.example.taskmaster.data.mapper.EntityToDomain
import com.example.taskmaster.domain.model.project.Project

class ProjectEntityToDomainMapper: EntityToDomain<ProjectEntity, Project> {
    override fun ProjectEntity.toDomainModel(): Project {
        return Project(
            id = this.id,
            name = this.name,
            description = this.description,
            address = this.address,
            thumbnailUrl = this.thumbnailUrl,
            isDeleted = this.isDeleted == 1,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }
}