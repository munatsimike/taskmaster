package com.example.taskmaster.domain.useCases.auth

import com.example.taskmaster.data.local.preferences.AccessToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetAccessTokenUseCase : GetAccessTokenUseCase {
    override suspend fun invoke(): Flow<AccessToken> = flow {
        emit(AccessToken(value = "abc123"))
    }
}