package com.example.taskmaster.data.remote.api.service

import com.example.taskmaster.data.remote.dto.dashboard.DashboardAPiResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DashboardService {

    @GET("projects/dashboard/{id}")
    suspend fun getDashboard(@Path("id") projectId: String): Response<DashboardAPiResponseDto>

}