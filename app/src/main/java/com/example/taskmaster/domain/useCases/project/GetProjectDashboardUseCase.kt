package com.example.taskmaster.domain.useCases.project

import com.example.taskmaster.data.remote.api.NetworkResponse
import com.example.taskmaster.data.repository.ProjectsRepo
import com.example.taskmaster.ui.model.DashboardData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProjectDashboardUseCase @Inject constructor (private val dataRepositoryImp: ProjectsRepo){
    operator fun invoke(projectId: String): Flow<NetworkResponse<DashboardData>> {
      return  dataRepositoryImp.getProjectDashboard(projectId)
    }
}