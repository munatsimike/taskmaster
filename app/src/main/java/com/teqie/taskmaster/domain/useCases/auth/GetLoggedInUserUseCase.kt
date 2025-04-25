package com.teqie.taskmaster.domain.useCases.auth

import com.teqie.taskmaster.data.repository.AuthRepositoryImp
import com.teqie.taskmaster.domain.LoggedInUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoggedInUserUseCase @Inject constructor(private val authRepository: AuthRepositoryImp) {
    suspend operator fun invoke(): Flow<LoggedInUser> {
        return authRepository.getLoggedInUser()
    }
}