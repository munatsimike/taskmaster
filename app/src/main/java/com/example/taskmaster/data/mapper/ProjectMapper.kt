package com.example.taskmaster.data.mapper

import com.example.taskmaster.data.remote.dto.project.CreateProjectRequestDto
import com.example.taskmaster.data.remote.dto.project.ProjectDto
import com.example.taskmaster.data.remote.dto.project.UpdateProjectRequestDto
import com.example.taskmaster.domain.model.project.Project
/**
 * Responsible for mapping between Project domain models and their corresponding
 * DTOs used for API requests (e.g., creating or updating a project).
 *
 * This mapper ensures a clear separation between the domain layer and the data layer
 * by transforming domain objects to data transfer objects (DTOs) before sending them
 * to the remote data source.
 */

object ProjectMapper {


    private fun ProjectDto.toDomainProject(): Project {
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

    fun List<ProjectDto>.toListOfDomainProject(): List<Project> {
        return this.map {
            it.toDomainProject()
        }
    }
    fun Project.toCreateNewProjectRequest(): CreateProjectRequestDto {
        return CreateProjectRequestDto(
            name = name,
            description = description,
            thumbnailUrl = thumbnailUrl
        )
    }

    fun Project.toUpdateProjectRequestDto(): UpdateProjectRequestDto {
        return UpdateProjectRequestDto(
            id = id,
            name = name,
            thumbnailUrl = thumbnailUrl,
            description = description
        )
    }
}