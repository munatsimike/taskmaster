package com.example.taskmaster.domain.useCases.project


import com.example.taskmaster.data.remote.api.Resource
import com.example.taskmaster.data.repository.ProjectsRepoImpl
import com.example.taskmaster.domain.model.project.Project
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProjectsUseCase @Inject constructor(private val projectsRepo: ProjectsRepoImpl) {
    operator fun invoke(): Flow<Resource<List<Project>>> {
        return projectsRepo.getProjects()
    }
}