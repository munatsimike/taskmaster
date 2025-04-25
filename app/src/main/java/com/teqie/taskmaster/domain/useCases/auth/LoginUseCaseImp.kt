package com.teqie.taskmaster.domain.useCases.auth


import com.teqie.taskmaster.domain.AuthRepository
import com.teqie.taskmaster.domain.LoginRequest
import com.teqie.taskmaster.domain.model.auth.User
import javax.inject.Inject

//LoginUseCaseImp is responsible for handling the login operation in the application's business logic layer.

class LoginUseCaseImp @Inject constructor(private val dataRepository: AuthRepository): LoginUseCase {
    // Delegate the login request to the userRepository and return the result.
    override suspend operator fun invoke(request: LoginRequest): Result<User> {
        return dataRepository.login(request)
    }
}