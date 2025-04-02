package com.example.taskflow.domain.usecases.user

import com.example.taskmaster.data.repository.AuthRepository
import com.example.taskmaster.domain.LoggedInUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoggedInUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Flow<LoggedInUser> {
        return authRepository.getLoggedInUser()
    }
}