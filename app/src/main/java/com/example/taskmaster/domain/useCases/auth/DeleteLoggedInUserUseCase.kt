package com.example.taskmaster.domain.useCases.auth

import com.example.taskmaster.data.repository.AuthRepository
import javax.inject.Inject

class DeleteLoggedInUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke() {
        authRepository.deleteLoggedInUser()
    }
}