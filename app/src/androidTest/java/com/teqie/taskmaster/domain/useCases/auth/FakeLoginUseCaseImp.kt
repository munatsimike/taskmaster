package com.teqie.taskmaster.domain.useCases.auth

import com.teqie.taskmaster.domain.LoginRequest
import com.teqie.taskmaster.domain.model.auth.User

class FakeLoginUseCaseImp: LoginUseCase {
    override suspend fun invoke(request: LoginRequest): Result<User> {
        return if (request.username == "user") {
            Result.success(User("avatar", "John", 0, "abc123"))
        } else {
            Result.failure(Exception("Invalid credentials"))
        }
    }
}