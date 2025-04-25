package com.teqie.taskmaster.data.remote

import com.teqie.taskmaster.data.remote.dto.ProjectResponseDto
import com.teqie.taskmaster.data.remote.dto.auth.LoginRequestDto
import com.teqie.taskmaster.data.remote.dto.dashboard.DashboardAPiResponseDto
import com.teqie.taskmaster.data.remote.dto.user.UserApiResponseDto
import com.teqie.taskmaster.domain.model.RemoteResponse
import com.teqie.taskmaster.domain.model.project.Project
import com.teqie.taskmaster.ui.model.ResponseMessage
import retrofit2.Response

interface RemoteDataSource {
    suspend fun login(loginRequest: LoginRequestDto): Response<UserApiResponseDto>

    suspend fun getProjects(): Response<List<ProjectResponseDto>>

    suspend fun addOrEditNewProject(projectRequest: Project, isEditing: Boolean): Response<out RemoteResponse>

    suspend fun deleteProject(projectId: String): Response<ResponseMessage>

    suspend fun getDashboard(projectId: String): Response<DashboardAPiResponseDto>
}