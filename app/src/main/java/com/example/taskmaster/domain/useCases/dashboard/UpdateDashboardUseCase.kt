package com.example.taskmaster.domain.useCases.dashboard

import com.example.taskmaster.data.remote.api.Resource
import com.example.taskmaster.domain.DashboardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateDashboardUseCase @Inject constructor(private  val dashboardRepository: DashboardRepository) {
    operator fun invoke(projectId: String): Flow<Resource<Unit>> {
        return  dashboardRepository.updateDashboard(projectId)
    }
}