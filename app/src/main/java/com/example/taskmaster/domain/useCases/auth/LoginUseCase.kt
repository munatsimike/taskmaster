package com.example.taskmaster.domain.useCases.auth

import com.example.taskmaster.domain.LoginRequest
import com.example.taskmaster.domain.model.auth.User

interface LoginUseCase {
    suspend operator fun invoke(request: LoginRequest): Result<User>
}