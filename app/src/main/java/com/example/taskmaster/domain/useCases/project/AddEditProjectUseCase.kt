package com.example.taskmaster.domain.useCases.project


import com.example.taskmaster.data.remote.api.NetworkResponse
import com.example.taskmaster.data.repository.ProjectsRepo
import com.example.taskmaster.ui.model.APIResponseMessage
import com.example.taskmaster.domain.model.project.Project
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddEditProjectUseCase @Inject constructor(private val projectsRepo: ProjectsRepo) {
    operator fun invoke(addOrEditProject: Project): Flow<NetworkResponse<APIResponseMessage>> {
       return projectsRepo.addOrEditNewProject(addOrEditProject)
    }
}