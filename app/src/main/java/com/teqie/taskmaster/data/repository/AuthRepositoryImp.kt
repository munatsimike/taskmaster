package com.teqie.taskmaster.data.repository

import com.teqie.taskmaster.data.local.LocalDataSource
import com.teqie.taskmaster.data.local.db.enties.LoggedInUserEntity
import com.teqie.taskmaster.data.local.preferences.AccessToken
import com.teqie.taskmaster.data.local.preferences.PreferenceKeys.ACCESS_TOKEN_KEY
import com.teqie.taskmaster.data.mapper.AuthMapper.toDomainUser
import com.teqie.taskmaster.data.mapper.AuthMapper.toDto
import com.teqie.taskmaster.data.mapper.AuthMapper.toLoggedInUser
import com.teqie.taskmaster.data.mapper.AuthMapper.toLoggedInUserEntity
import com.teqie.taskmaster.data.remote.RemoteDataSource
import com.teqie.taskmaster.domain.AuthRepository
import com.teqie.taskmaster.domain.LoggedInUser
import com.teqie.taskmaster.domain.LoginRequest
import com.teqie.taskmaster.domain.model.auth.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImp @Inject  constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
): AuthRepository {
    /**
     * This function interacts with Remotedatasource to perform login operations
     * @param loginRequest contains login credentials: username and password
     * @return  Result<User> containing user details if login is successful or error message if it fails
     * */

    override suspend fun login(loginRequest: LoginRequest): Result<User> {
        // attempt to login and receives a Response object containing the server response as UserAPiResponse
        val result = remoteDataSource.login(loginRequest.toDto())
        return if (result.isSuccessful) {
            //convert the response body to a User domain model and wrap it in a success Result.
            result.body()?.let { response ->
                saveAccessToken(AccessToken(value = response.token))
                saveLoggedInUser(response.toLoggedInUserEntity())
                Result.success(response.toDomainUser())

            } ?: Result.failure(Exception("Empty response body"))
        } else {
            Result.failure(Exception(result.errorBody()?.string() ?: ""))
        }
    }

    override suspend fun isTokenValid(): Response<Unit> {
      return  remoteDataSource.isTokenValid()
    }

    private suspend fun saveAccessToken(accessToken: AccessToken) {
        localDataSource.saveAccessToken(accessToken)
    }

    fun getAccessToken(key: String = ACCESS_TOKEN_KEY): Flow<AccessToken> {
        return localDataSource.getAccessToken(key)
    }

    suspend fun logout() {
        deleteAccessToken()
        clearLoggedInUser()
    }

    suspend fun deleteLoggedInUser() {
        localDataSource.deleteLoggedInUser()
    }

    private suspend fun clearLoggedInUser() {
        localDataSource.deleteLoggedInUser()
    }

    private suspend fun deleteAccessToken() {
        localDataSource.deleteAccessToken(AccessToken(value = ""))
    }

    suspend fun getLoggedInUser(): Flow<LoggedInUser> = flow {
        localDataSource.getLoggedInUser().collect { user ->
            if (user !== null) {
                emit(user.toLoggedInUser())
            }
        }
    }

    private suspend fun saveLoggedInUser(userEntity: LoggedInUserEntity) {
        localDataSource.saveLoggedInUser(userEntity)
    }
}