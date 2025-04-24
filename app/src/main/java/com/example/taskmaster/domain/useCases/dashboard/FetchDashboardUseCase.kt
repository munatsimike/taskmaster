package com.example.taskmaster.domain.useCases.dashboard

import com.example.taskmaster.data.remote.api.Resource
import com.example.taskmaster.domain.DashboardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchDashboardUseCase @Inject constructor (private val dashboardRepository: DashboardRepository){
    operator fun invoke(projectId: String): Flow<Resource<com.example.taskmaster.domain.model.DashboardData>> {
      return  dashboardRepository.fetchDashboard(projectId)
    }
}