package com.teqie.taskmaster.domain.useCases.project

import com.teqie.taskmaster.domain.ProjectRepository
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteProjectUseCase @Inject constructor(private val projectsRepo: ProjectRepository) {
    operator fun invoke(projectId: String): Flow<Resource<ResponseMessage>> {
        return projectsRepo.deleteProject(projectId)
    }
}