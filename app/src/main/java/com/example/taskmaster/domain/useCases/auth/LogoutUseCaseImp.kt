package com.example.taskmaster.domain.useCases.auth

import com.example.taskmaster.data.repository.AuthRepository
import javax.inject.Inject


class LogoutUseCaseImp @Inject constructor(private val authRepository: AuthRepository): LogoutUseCase{
    override suspend fun logout(){
        authRepository.logout()
    }
}