package com.teqie.taskmaster.domain.useCases.auth

import com.teqie.taskmaster.domain.LoginRequest
import com.teqie.taskmaster.domain.model.auth.User

interface LoginUseCase {
    suspend operator fun invoke(request: LoginRequest): Result<User>
}