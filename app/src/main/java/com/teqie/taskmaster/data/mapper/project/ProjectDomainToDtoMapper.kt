package com.teqie.taskmaster.data.mapper.project

import com.teqie.taskmaster.data.remote.dto.CreateProjectRequestDto
import com.teqie.taskmaster.data.remote.dto.UpdateProjectRequestDto
import com.teqie.taskmaster.data.remote.dto.dashboard.TotalsResponseDto
import com.teqie.taskmaster.domain.model.Totals
import com.teqie.taskmaster.domain.model.project.Project

/**
 * Responsible for mapping between Project domain models and their corresponding
 * DTOs used for API requests (e.g., creating or updating a project).
 *
 * This mapper ensures a clear separation between the domain layer and the data layer
 * by transforming domain objects to data transfer objects (DTOs) before sending them
 * to the remote data source.
 */

object ProjectDomainToDtoMapper {

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

    fun TotalsResponseDto.toTotalsModel(): Totals {
        return Totals(
            resolvedOrfis = this.resolvedOrfis,
            totalAmount = this.totalAmount,
            totalDuration = this.totalDuration,
            totalOrfis = this.totalOrfis,
            totalPaid = this.totalPaid,
            totalScheduleProgress = this.totalScheduleProgress
        )
    }
}