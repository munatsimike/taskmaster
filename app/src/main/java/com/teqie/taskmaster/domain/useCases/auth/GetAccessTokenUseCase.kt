package com.teqie.taskmaster.domain.useCases.auth

import com.teqie.taskmaster.data.local.preferences.AccessToken
import kotlinx.coroutines.flow.Flow

interface GetAccessTokenUseCase {
    suspend fun invoke(): Flow<AccessToken>
}