package com.example.taskmaster.domain.useCases.auth

import com.example.taskmaster.data.repository.AuthRepositoryImp
import javax.inject.Inject


class LogoutUseCaseImp @Inject constructor(private val authRepository: AuthRepositoryImp): LogoutUseCase{
    override suspend fun logout(){
        authRepository.logout()
    }
}