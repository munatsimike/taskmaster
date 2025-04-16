package com.example.taskmaster.domain.useCases.auth


import com.example.taskmaster.domain.DataRepository
import com.example.taskmaster.domain.LoginRequest
import com.example.taskmaster.domain.model.auth.User
import javax.inject.Inject

//LoginUseCaseImp is responsible for handling the login operation in the application's business logic layer.

class LoginUseCaseImp @Inject constructor(private val dataRepository: DataRepository): LoginUseCase {
    // Delegate the login request to the userRepository and return the result.
    override suspend operator fun invoke(request: LoginRequest): Result<User> {
        return dataRepository.login(request)
    }
}