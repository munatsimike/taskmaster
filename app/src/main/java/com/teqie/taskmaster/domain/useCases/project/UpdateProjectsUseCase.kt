package com.teqie.taskmaster.domain.useCases.project

import com.teqie.taskmaster.domain.ProjectRepository
import com.teqie.taskmaster.domain.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProjectsUseCase @Inject constructor(private val projectRepository: ProjectRepository) {
    operator fun invoke(): Flow<Resource<Unit>> {
        return projectRepository.syncProjectsToLocalDb()
    }
}