package com.example.taskmaster.domain

import com.example.taskmaster.data.remote.api.Resource
import com.example.taskmaster.domain.model.DashboardData

import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun fetchDashboard(projectId: String): Flow<Resource<DashboardData>>
    fun updateDashboard(projectId: String): Flow<Resource<Unit>>
}