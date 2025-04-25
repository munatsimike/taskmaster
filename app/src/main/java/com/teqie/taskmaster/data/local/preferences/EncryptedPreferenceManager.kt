package com.teqie.taskmaster.data.local.preferences

import android.content.Context
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.teqie.taskmaster.data.local.preferences.PreferenceKeys.ACCESS_TOKEN_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.Collections
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptedPreferenceManager @Inject constructor(@ApplicationContext private val context: Context) {

    // Using AndroidX Security library to generate or retrieve a master key alias
    private val masterKeyAlias by lazy { MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC) }

    // Creating EncryptedSharedPreferences instance with AES encryption
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secret_shared_preferences",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val editor = sharedPreferences.edit()
    private val cache: MutableMap<String, String> = Collections.synchronizedMap(mutableMapOf())

    fun getData(key: String = ACCESS_TOKEN_KEY): Flow<AccessToken> = flow {
        // Attempt to get the value from the cache first
        val tokenValue = cache[key] ?: sharedPreferences.getString(key, null)?.also {
            // Cache the result for faster future access
            cache[key] = it
        } ?: "" // Default to an empty string if the value is not found
        emit(AccessToken(value = tokenValue))
    }

    suspend fun saveUpdate(accessToken: AccessToken) = withContext(Dispatchers.IO) {
        val tokenKey = accessToken.key
        val value = accessToken.value

        try {
            // Update the cache and SharedPreferences
            cache[tokenKey] = value
            editor.putString(tokenKey, value).apply()
        } catch (e: Exception) {
            // Handle potential exceptions (logging, error handling, etc.)
            Log.e("EncryptedPreferenceManager", "Error saving data for key: $tokenKey", e)
        }
    }
}