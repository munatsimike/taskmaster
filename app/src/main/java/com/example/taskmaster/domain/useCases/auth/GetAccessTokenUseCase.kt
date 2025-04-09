package com.example.taskmaster.domain.useCases.auth

import com.example.taskmaster.data.local.preferences.AccessToken
import kotlinx.coroutines.flow.Flow

interface GetAccessTokenUseCase {
    suspend fun invoke(): Flow<AccessToken>
}