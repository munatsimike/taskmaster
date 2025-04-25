package com.teqie.taskmaster.domain.useCases.dashboard

import com.teqie.taskmaster.data.remote.api.Resource
import com.teqie.taskmaster.domain.DashboardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateDashboardUseCase @Inject constructor(private  val dashboardRepository: DashboardRepository) {
    operator fun invoke(projectId: String): Flow<Resource<Unit>> {
        return  dashboardRepository.updateDashboard(projectId)
    }
}