package com.teqie.taskmaster.data.remote.api.service

import com.teqie.taskmaster.data.remote.dto.auth.LoginRequestDto
import com.teqie.taskmaster.data.remote.dto.user.UserApiResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * sends a login in request to the server and returns the result
 * The `request` parameter contains the login credentials: username and password
 * @return A Response object containing the server response as UserAPiResponse
 */
interface AuthService {
    @POST("auth/login")
   suspend fun login(@Body request: LoginRequestDto): Response<UserApiResponseDto>

    @GET("users/me")
    suspend fun getCurrentUser(): Response<Unit>
}