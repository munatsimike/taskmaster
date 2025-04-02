package com.example.taskmaster.domain.useCases.auth

import com.example.taskmaster.data.repository.AuthRepository
import javax.inject.Inject


class LogoutUseCase @Inject()  constructor(private val authRepository: AuthRepository){
    suspend fun logout(){
        authRepository.logout()
    }
}