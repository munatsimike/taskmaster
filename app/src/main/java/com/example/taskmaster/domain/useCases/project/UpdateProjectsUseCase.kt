package com.example.taskmaster.domain.useCases.project

import com.example.taskmaster.data.remote.api.Resource
import com.example.taskmaster.domain.ProjectRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProjectsUseCase @Inject constructor(private val projectRepository: ProjectRepository) {
    operator fun invoke(): Flow<Resource<Unit>> {
        return projectRepository.updateProjects()
    }
}