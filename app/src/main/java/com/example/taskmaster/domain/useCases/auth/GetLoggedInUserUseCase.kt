package com.example.taskmaster.domain.useCases.auth

import com.example.taskmaster.data.repository.AuthRepositoryImp
import com.example.taskmaster.domain.LoggedInUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoggedInUserUseCase @Inject constructor(private val authRepository: AuthRepositoryImp) {
    suspend operator fun invoke(): Flow<LoggedInUser> {
        return authRepository.getLoggedInUser()
    }
}