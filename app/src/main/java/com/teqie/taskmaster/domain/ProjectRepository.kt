package com.teqie.taskmaster.domain

import com.teqie.taskmaster.data.local.db.enties.ProjectEntity
import com.teqie.taskmaster.domain.model.project.Project
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {

    fun addOrEditNewProject(
        addEditProject: Project,
        isEditing: Boolean
    ): Flow<Resource<ResponseMessage>>

    fun deleteProject(projectId: String): Flow<Resource<ResponseMessage>>

    fun syncProjectsToLocalDb(): Flow<Resource<Unit>>

    fun getProjects(): Flow<Resource<List<Project>>>

    fun getAllProjects(): Flow<List<ProjectEntity>>
}