package com.teqie.taskmaster.data.remote.api.service

import com.teqie.taskmaster.data.remote.dto.schedule.ScheduleFetchResponse
import com.teqie.taskmaster.data.remote.dto.schedule.UpdateScheduleRequest
import com.teqie.taskmaster.data.remote.dto.schedule.UpdateScheduleResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ScheduleService {
    @GET("api/schedules/project/{id}")
    suspend fun getSchedule(@Path("id") projectId: String): Response<List<ScheduleFetchResponse>>

    @PUT("api/schedules/{id}")
    suspend fun upDateSchedule(@Path("id") scheduleId: String, @Body updateScheduleRequest: UpdateScheduleRequest): Response<UpdateScheduleResponseDto>
}