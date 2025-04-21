package com.example.taskmaster.domain.useCases.project


import com.example.taskmaster.data.remote.api.Resource
import com.example.taskmaster.domain.ProjectRepository
import com.example.taskmaster.domain.model.project.Project
import com.example.taskmaster.ui.model.APIResponseMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddEditProjectUseCase @Inject constructor(private val projectsRepo: ProjectRepository) {
    operator fun invoke(addOrEditProject: Project, isEditing: Boolean): Flow<Resource<APIResponseMessage>> {
       return projectsRepo.addOrEditNewProject(addOrEditProject,isEditing)
    }
}