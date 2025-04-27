package com.teqie.taskmaster.domain.useCases.dashboard

import com.teqie.taskmaster.domain.DashboardRepository
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.DashboardData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchDashboardUseCase @Inject constructor (private val dashboardRepository: DashboardRepository){
    operator fun invoke(projectId: String): Flow<Resource<DashboardData>> {
      return  dashboardRepository.fetchDashboard(projectId)
    }
}