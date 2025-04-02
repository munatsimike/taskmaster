package com.example.taskmaster.data.remote.api.service

import com.example.taskmaster.data.remote.dto.user.UserApiResponseDto
import com.example.taskmaster.domain.LoginRequest

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * sends a login in request to the server and returns the result
 * The `request` parameter contains the login credentials: username and password
 * @return A Response object containing the server response as UserAPiResponse
 */
interface AuthService {
    @POST("auth/login")
   suspend fun login(@Body request: LoginRequest): Response<UserApiResponseDto>
}