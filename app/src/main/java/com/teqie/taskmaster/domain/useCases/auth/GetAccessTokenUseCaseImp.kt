package com.teqie.taskmaster.domain.useCases.auth

import com.teqie.taskmaster.data.local.preferences.AccessToken
import com.teqie.taskmaster.data.repository.AuthRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccessTokenUseCaseImp @Inject constructor(private val authRepository: AuthRepositoryImp): GetAccessTokenUseCase {
    override suspend operator fun invoke() : Flow<AccessToken> {
      return authRepository.getAccessToken()
    }
}