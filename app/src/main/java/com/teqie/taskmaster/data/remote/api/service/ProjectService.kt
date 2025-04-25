package com.teqie.taskmaster.data.remote.api.service

import com.teqie.taskmaster.data.remote.dto.AddNewProjectResponseDto
import com.teqie.taskmaster.data.remote.dto.CreateProjectRequestDto
import com.teqie.taskmaster.data.remote.dto.ProjectResponseDto
import com.teqie.taskmaster.data.remote.dto.UpdateProjectRequestDto
import com.teqie.taskmaster.data.remote.dto.UpdateProjectResponseDto
import com.teqie.taskmaster.ui.model.ResponseMessage
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
    suspend fun getProjects(): Response<List<ProjectResponseDto>>

    @POST("projects")
    suspend fun createNewProject(@Body addProjectRequest: CreateProjectRequestDto): Response<AddNewProjectResponseDto>

    @DELETE("projects/{id}")
    suspend fun deleteProject(@Path("id") projectId: String): Response<ResponseMessage>

    @PUT("projects/{id}")
    suspend fun updateProject(
        @Path("id") projectId: String,
        @Body addProjectRequest: UpdateProjectRequestDto
    ): Response<UpdateProjectResponseDto>
}