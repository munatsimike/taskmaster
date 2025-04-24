package com.example.taskmaster.data.remote

import com.example.taskmaster.data.remote.dto.ProjectResponseDto
import com.example.taskmaster.data.remote.dto.dashboard.DashboardAPiResponseDto
import com.example.taskmaster.data.remote.dto.user.UserApiResponseDto
import com.example.taskmaster.domain.LoginRequest
import com.example.taskmaster.domain.model.APIResponse
import com.example.taskmaster.domain.model.project.Project
import com.example.taskmaster.ui.model.APIResponseMessage
import retrofit2.Response

interface RemoteDataSource {
    suspend fun login(loginRequest: LoginRequest): Response<UserApiResponseDto>

    suspend fun getProjects(): Response<List<ProjectResponseDto>>

    suspend fun addOrEditNewProject(projectRequest: Project, isEditing: Boolean): Response<out APIResponse>

    suspend fun deleteProject(projectId: String): Response<APIResponseMessage>

    suspend fun getDashboard(projectId: String): Response<DashboardAPiResponseDto>
}