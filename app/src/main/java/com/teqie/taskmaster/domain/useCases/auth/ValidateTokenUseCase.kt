package com.teqie.taskmaster.domain.useCases.auth

import com.teqie.taskmaster.domain.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class ValidateTokenUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Response<Unit>{
      return  authRepository.isTokenValid()
    }
}