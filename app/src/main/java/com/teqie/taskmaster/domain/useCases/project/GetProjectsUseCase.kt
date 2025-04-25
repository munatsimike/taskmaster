package com.teqie.taskmaster.domain.useCases.project


import com.teqie.taskmaster.data.remote.api.Resource
import com.teqie.taskmaster.domain.ProjectRepository
import com.teqie.taskmaster.domain.model.project.Project
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProjectsUseCase @Inject constructor(private val projectsRepo: ProjectRepository) {
    operator fun invoke(): Flow<Resource<List<Project>>> {
        return projectsRepo.getProjects()
    }
}