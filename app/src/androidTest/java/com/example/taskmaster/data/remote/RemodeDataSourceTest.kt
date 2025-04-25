package com.example.taskmaster.data.remote

import com.example.taskmaster.data.mapper.AuthMapper.toDto
import com.example.taskmaster.data.remote.api.service.AuthService
import com.example.taskmaster.data.remote.api.service.DashboardService
import com.example.taskmaster.data.remote.api.service.ProjectService
import com.example.taskmaster.data.remote.dto.user.UserApiResponseDto
import com.example.taskmaster.data.remote.dto.user.UserDetailsDto
import com.example.taskmaster.domain.LoginRequest
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RemoteDataSourceTest {

    private lateinit var authService: AuthService
    private lateinit var remoteDataSource: RemoteDataSourceImpl
    private lateinit var projectService: ProjectService
    private lateinit var dashboardService: DashboardService

    @Before
    fun setup() {
        authService = mockk<AuthService>()
        dashboardService = mockk<DashboardService>()
        projectService = mockk<ProjectService>()
        remoteDataSource = RemoteDataSourceImpl(authService, projectService, dashboardService)
    }

    @Test
    fun `login returns users details when login is successful`() = runTest {
        val expectedResponse = Response.success(
            UserApiResponseDto(
                email = "test@example.com",
                id = "1",
                isSuperUser = 1,
                name = "Test User",
                phone = "1234567890",
                token = "abc123",
                userDetails = UserDetailsDto(
                    address = "123 Street Name",
                    avatar = "https://example.com/avatar.jpg",
                    email = "detail@example.com",
                    id = "10",
                    name = "John Detail",
                    phone = "0987654321"
                )
            )
        )

        coEvery { authService.login(LoginRequest("user2", "user123").toDto()) } returns expectedResponse
        val response = remoteDataSource.login(LoginRequest("user2", "user123").toDto())
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()?.userDetails?.name).isEqualTo("John Detail")
    }

    @Test
    fun `login should return 401 when unauthorized`() = runTest {

        val errorJson = """{ "message": "Invalid credentials" }"""
        val errorBody = errorJson.toResponseBody("application/json".toMediaType())

        val errorResponse = Response.error<UserApiResponseDto>(
            401,
            errorBody
        )

        coEvery { authService.login(any()) } returns errorResponse

        val result = remoteDataSource.login(LoginRequest("wrong", "creds").toDto())
        assertThat(result.code()).isEqualTo(401)
        val errorString = result.errorBody()?.string()
        assertThat(errorString).contains("Invalid credentials")
    }
}