package com.example.taskmaster.domain.useCases.auth

import com.example.taskmaster.data.repository.AuthRepositoryImp
import javax.inject.Inject

class DeleteLoggedInUserUseCase @Inject constructor(private val authRepository: AuthRepositoryImp) {
    suspend operator fun invoke() {
        authRepository.deleteLoggedInUser()
    }
}