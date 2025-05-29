package com.teqie.taskmaster.data.mapper.project

import com.teqie.taskmaster.data.local.db.enties.ProjectEntity
import com.teqie.taskmaster.data.mapper.EntityToDomain
import com.teqie.taskmaster.domain.model.project.Project

object ProjectEntityToDomainMapper: EntityToDomain<ProjectEntity, Project> {
    override fun ProjectEntity.toDomainModel(): Project {
        return Project(
            id = this.id,
            name = this.name,
            description = this.description,
            address = this.address,
            thumbnailUrl = this.thumbnailUrl,
            isDeleted = isDeleted.toInt() == 1,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }
}