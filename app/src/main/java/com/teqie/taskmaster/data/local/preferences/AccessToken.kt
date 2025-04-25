package com.teqie.taskmaster.data.local.preferences

import com.teqie.taskmaster.data.local.preferences.PreferenceKeys.ACCESS_TOKEN_KEY
import java.time.Instant

data class AccessToken (
    val key: String = ACCESS_TOKEN_KEY,
    val value: String = "",
    val expiresAt: Instant? = null // Optional expiration time
) {
    // Check if the token is not empty
    fun isNotEmpty(): Boolean = value.isNotEmpty()

    // Check if the token is expired
    fun isExpired(): Boolean {
        return expiresAt?.let { Instant.now().isAfter(it) }
            ?: false // Assume not expired if no expiration set
    }
}