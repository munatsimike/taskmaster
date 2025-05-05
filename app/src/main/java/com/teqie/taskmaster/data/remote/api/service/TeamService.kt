package com.teqie.taskmaster.data.remote.api.service

import com.teqie.taskmaster.data.remote.dto.user.CreateUserResponseDto
import com.teqie.taskmaster.data.remote.dto.user.TeamsResponseItemDto
import com.teqie.taskmaster.domain.model.user.CreateUserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TeamService {
    @GET("users/project/{id}")
    suspend fun getTeamsByProject(@Path("id") projectId: String): Response<List<TeamsResponseItemDto>>

    @POST("users/create-and-assign")
    suspend fun createAssignUser(@Body createUserRequest: CreateUserRequest): Response<CreateUserResponseDto>
}