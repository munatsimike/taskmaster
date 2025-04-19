package com.example.taskmaster.data.repository

import com.example.taskmaster.data.local.LocalDataSourceImpl
import com.example.taskmaster.data.local.preferences.AccessToken
import com.example.taskmaster.data.local.preferences.PreferenceKeys.ACCESS_TOKEN_KEY
import com.example.taskmaster.data.remote.RemoteDataSourceImpl
import com.example.taskmaster.data.remote.dto.user.UserApiResponseDto
import com.example.taskmaster.data.remote.dto.user.UserDetailsDto
import com.example.taskmaster.domain.LoginRequest
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AuthRepositoryTest {

    private lateinit var localDataSource: LocalDataSourceImpl
    private lateinit var remoteDataSource: RemoteDataSourceImpl
    private lateinit var authRepository: AuthRepositoryImp

    @Before
    fun setup() {
        localDataSource = mockk<LocalDataSourceImpl>(relaxed = true)
        remoteDataSource = mockk<RemoteDataSourceImpl>()
        authRepository = AuthRepositoryImp(remoteDataSource, localDataSource)
    }

    @Test
    fun `login returns success when RemoteDataSource responds with 200`() = runTest {
        val loginRequest = LoginRequest("user", "pass")
        val apiResponse = UserApiResponseDto(
            email = "test@example.com",
            id = "1",
            isSuperUser = 0,
            name = "Test",
            phone = "123",
            token = "abc123",
            userDetails = UserDetailsDto("Addr", "avatar", "email", "id", "name", "123")
        )

        coEvery { remoteDataSource.login(loginRequest) } returns Response.success(apiResponse)

        val result = authRepository.login(loginRequest)

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()?.name).isEqualTo("Test")

        coVerify {
            localDataSource.saveAccessToken(
                AccessToken(
                    key = ACCESS_TOKEN_KEY, // or just "access_token"
                    value = "abc123",
                    expiresAt = null
                )
            )
        }
        coVerify { localDataSource.saveLoggedInUser(any()) }
    }
}