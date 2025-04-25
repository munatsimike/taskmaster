package com.teqie.taskmaster.domain.useCases.project


import com.teqie.taskmaster.data.remote.api.Resource
import com.teqie.taskmaster.domain.ProjectRepository
import com.teqie.taskmaster.domain.model.project.Project
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddEditProjectUseCase @Inject constructor(private val projectsRepo: ProjectRepository) {
    operator fun invoke(addOrEditProject: Project, isEditing: Boolean): Flow<Resource<ResponseMessage>> {
       return projectsRepo.addOrEditNewProject(addOrEditProject,isEditing)
    }
}