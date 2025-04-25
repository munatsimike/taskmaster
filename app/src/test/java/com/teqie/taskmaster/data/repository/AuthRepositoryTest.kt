package com.teqie.taskmaster.data.repository

import com.teqie.taskmaster.data.local.LocalDataSourceImpl
import com.teqie.taskmaster.data.local.preferences.AccessToken
import com.teqie.taskmaster.data.local.preferences.PreferenceKeys.ACCESS_TOKEN_KEY
import com.teqie.taskmaster.data.remote.RemoteDataSourceImpl
import com.teqie.taskmaster.data.remote.dto.user.UserApiResponseDto
import com.teqie.taskmaster.data.remote.dto.user.UserDetailsDto
import com.teqie.taskmaster.domain.LoginRequest
import com.google.common.truth.Truth.assertThat
import com.teqie.taskmaster.data.mapper.AuthMapper.toDto
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

        coEvery { remoteDataSource.login(loginRequest.toDto()) } returns Response.success(apiResponse)

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