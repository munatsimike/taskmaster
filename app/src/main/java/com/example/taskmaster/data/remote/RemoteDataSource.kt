package com.example.taskmaster.data.remote

import com.example.taskmaster.data.remote.api.service.AuthService
import com.example.taskmaster.data.remote.dto.user.UserApiResponseDto
import com.example.taskmaster.domain.LoginRequest
import retrofit2.Response
import javax.inject.Inject

/**
 *  * RemoteDataSource is responsible for interacting with the remote server to access and manage data.
 *  * It acts as a bridge between the data service (Retrofit interface) and the repository layer,
 */
class RemoteDataSource @Inject constructor(
    private val authService: AuthService,
) {
    /**dataService
     * attempts to login through the data service and returns server response wrapped in a response object
     * @param loginRequest contains the username and password
     */
    suspend fun login(loginRequest: LoginRequest): Response<UserApiResponseDto> {
        // make api request using retrofit service and returns the server response
        return authService.login(loginRequest)
    }
}