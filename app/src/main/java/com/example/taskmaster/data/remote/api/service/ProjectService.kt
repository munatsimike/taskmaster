package com.example.taskmaster.data.remote.api.service

import com.example.taskmaster.data.remote.dto.AddNewProjectResponseDto
import com.example.taskmaster.data.remote.dto.CreateProjectRequestDto
import com.example.taskmaster.data.remote.dto.ProjectDto
import com.example.taskmaster.data.remote.dto.UpdateProjectRequestDto
import com.example.taskmaster.data.remote.dto.UpdateProjectResponseDto
import com.example.taskmaster.data.remote.dto.dashboard.DashboardAPiResponseDto
import com.example.taskmaster.ui.model.APIResponseMessage
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProjectService {
    /**
     * Fetches the list of projects from the server
     * @return A Response object containing a list of projects
     */
    @GET("projects")
    suspend fun getProjects(): Response<List<ProjectDto>>

    @GET("projects/dashboard/{id}")
    suspend fun getDashboard(@Path("id") projectId: String): Response<DashboardAPiResponseDto>

    @POST("projects")
    suspend fun createNewProject(@Body addProjectRequest: CreateProjectRequestDto): Response<AddNewProjectResponseDto>

    @DELETE("projects/{id}")
    suspend fun deleteProject(@Path("id") projectId: String): Response<APIResponseMessage>

    @PUT("projects/{id}")
    suspend fun updateProject(
        @Path("id") projectId: String,
        @Body addProjectRequest: UpdateProjectRequestDto
    ): Response<UpdateProjectResponseDto>
}