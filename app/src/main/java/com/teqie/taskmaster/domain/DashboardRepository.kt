package com.teqie.taskmaster.domain

import com.teqie.taskmaster.data.remote.api.Resource
import com.teqie.taskmaster.domain.model.DashboardData

import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun fetchDashboard(projectId: String): Flow<Resource<DashboardData>>
    fun updateDashboard(projectId: String): Flow<Resource<Unit>>
}