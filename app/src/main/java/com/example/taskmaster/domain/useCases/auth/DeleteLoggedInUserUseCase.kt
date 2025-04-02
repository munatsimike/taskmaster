package com.example.taskflow.domain.usecases.user

import com.example.taskmaster.data.repository.AuthRepository
import javax.inject.Inject

class DeleteLoggedInUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke() {
        authRepository.deleteLoggedInUser()
    }
}