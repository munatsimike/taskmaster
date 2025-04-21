package com.example.taskmaster.domain.useCases.project

import com.example.taskmaster.data.remote.api.Resource
import com.example.taskmaster.domain.ProjectRepository
import com.example.taskmaster.ui.model.DashboardData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProjectDashboardUseCase @Inject constructor (private val projectsRepoImpl: ProjectRepository){
    operator fun invoke(projectId: String): Flow<Resource<DashboardData>> {
      return  projectsRepoImpl.getProjectDashboard(projectId)
    }
}