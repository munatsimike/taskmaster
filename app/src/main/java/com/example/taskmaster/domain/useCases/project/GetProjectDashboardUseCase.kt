package com.example.taskmaster.domain.useCases.project

import com.example.taskmaster.data.remote.api.Resource
import com.example.taskmaster.data.repository.ProjectsRepoImpl
import com.example.taskmaster.ui.model.DashboardData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProjectDashboardUseCase @Inject constructor (private val projectsRepoImpl: ProjectsRepoImpl){
    operator fun invoke(projectId: String): Flow<Resource<DashboardData>> {
      return  projectsRepoImpl.getProjectDashboard(projectId)
    }
}