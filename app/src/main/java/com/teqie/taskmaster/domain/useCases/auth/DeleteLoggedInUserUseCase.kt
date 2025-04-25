package com.teqie.taskmaster.domain.useCases.auth

import com.teqie.taskmaster.data.repository.AuthRepositoryImp
import javax.inject.Inject

class DeleteLoggedInUserUseCase @Inject constructor(private val authRepository: AuthRepositoryImp) {
    suspend operator fun invoke() {
        authRepository.deleteLoggedInUser()
    }
}