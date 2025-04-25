package com.teqie.taskmaster.data.local.preferences

import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TokenProvider @Inject constructor(private val encryptedPreferenceManager: EncryptedPreferenceManager) {
    // Fetches the latest access token from EncryptedPreferenceManager
    suspend fun getAccessToken(): AccessToken {
        return encryptedPreferenceManager.getData().first()
    }
}