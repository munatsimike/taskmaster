package com.example.taskmaster.domain.useCases.auth

import com.example.taskmaster.data.local.preferences.AccessToken
import com.example.taskmaster.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccessTokenUseCaseImp @Inject constructor(private val authRepository: AuthRepository): GetAccessTokenUseCase {
    override suspend operator fun invoke() : Flow<AccessToken> {
      return authRepository.getAccessToken()
    }
}