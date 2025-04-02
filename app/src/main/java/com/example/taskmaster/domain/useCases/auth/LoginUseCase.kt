package com.example.taskmaster.domain.useCases.auth


import com.example.taskmaster.domain.DataRepository
import com.example.taskmaster.domain.LoginRequest
import com.example.taskmaster.domain.model.User
import javax.inject.Inject

//LoginUseCase is responsible for handling the login operation in the application's business logic layer.

class LoginUseCase @Inject constructor(private val dataRepository: DataRepository) {
    // Delegate the login request to the userRepository and return the result.
    suspend operator fun invoke(loginRequest: LoginRequest): Result<User> {
        return dataRepository.login(loginRequest)
    }
}