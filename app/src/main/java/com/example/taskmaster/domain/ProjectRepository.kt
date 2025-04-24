package com.example.taskmaster.domain

import com.example.taskmaster.data.local.db.enties.ProjectEntity
import com.example.taskmaster.data.remote.api.Resource
import com.example.taskmaster.domain.model.project.Project
import com.example.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {

    fun addOrEditNewProject(
        addEditProject: Project,
        isEditing: Boolean
    ): Flow<Resource<ResponseMessage>>

    fun deleteProject(projectId: String): Flow<Resource<ResponseMessage>>

   fun updateProjects():  Flow<Resource<Unit>>

    fun getProjects(): Flow<Resource<List<Project>>>

    suspend fun saveProjectsToDb(projects: List<ProjectEntity>)
    fun getAllProjects(): Flow<List<ProjectEntity>>
}