package com.example.taskmaster.data.local


import com.example.taskmaster.data.local.db.dao.LoggedInUserDao
import com.example.taskmaster.data.local.db.enties.LoggedInUserEntity
import com.example.taskmaster.data.local.preferences.AccessToken
import com.example.taskmaster.data.local.preferences.EncryptedPreferenceManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val encryptedPreferenceManager: EncryptedPreferenceManager,
    private val loggedInUserDao: LoggedInUserDao,
) {

    fun getAccessToken(key: String): Flow<AccessToken> {
        return encryptedPreferenceManager.getData(key)
    }

    suspend fun saveAccessToken(accessToken: AccessToken) {
        encryptedPreferenceManager.saveUpdate(accessToken)
    }

    suspend fun deleteAccessToken(accessToken: AccessToken) {
        encryptedPreferenceManager.saveUpdate(accessToken) // Clear the token
    }

    suspend fun saveLoggedInUser(userEntity: LoggedInUserEntity) {
        loggedInUserDao.saveUser(userEntity)
    }

    suspend fun deleteLoggedInUser() {
        loggedInUserDao.deleteLoggedInUser()
    }

    suspend fun getLoggedInUser(): Flow<LoggedInUserEntity?> {
        return loggedInUserDao.getLoggedInUser()
    }
}