package com.example.taskmaster.domain

import com.example.taskmaster.data.local.db.enties.ProjectEntity
import com.example.taskmaster.data.remote.api.Resource
import com.example.taskmaster.domain.model.project.Project
import com.example.taskmaster.ui.model.APIResponseMessage
import com.example.taskmaster.ui.model.DashboardData
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {

    fun addOrEditNewProject(
        addEditProject: Project,
        isEditing: Boolean
    ): Flow<Resource<APIResponseMessage>>

    fun deleteProject(projectId: String): Flow<Resource<APIResponseMessage>>

    fun getProjects(): Flow<Resource<List<Project>>>

    fun getProjectDashboard(projectId: String): Flow<Resource<DashboardData>>
    suspend fun saveProjectsToDb(entity: ProjectEntity)
}