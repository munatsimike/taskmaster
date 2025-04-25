package com.teqie.taskmaster.domain.useCases.auth

import com.teqie.taskmaster.data.repository.AuthRepositoryImp
import javax.inject.Inject


class LogoutUseCaseImp @Inject constructor(private val authRepository: AuthRepositoryImp): LogoutUseCase{
    override suspend fun logout(){
        authRepository.logout()
    }
}