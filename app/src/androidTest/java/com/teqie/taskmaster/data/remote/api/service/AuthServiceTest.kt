package com.teqie.taskmaster.data.remote.api.service

import com.teqie.taskmaster.data.mapper.AuthMapper.toDto
import com.teqie.taskmaster.domain.LoginRequest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AuthServiceTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var mockWebServer: MockWebServer

    @Inject
    lateinit var authService: AuthService

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `login should return success when response is 200`() = runTest {
        val mockJson = """
          {
          "email": "test@example.com",
          "id": "1",
          "isSuperUser": 1,
          "name": "Test User",
          "phone": "1234567890",
          "token": "abc123",
          "userDetails": {
            "address": "123 Street Name",
            "avatar": "https://example.com/avatar.jpg",
            "email": "userdetail@example.com",
            "id": "10",
            "name": "John Detail",
            "phone": "0987654321"
          }
        }
        """.trimIndent()
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(mockJson)
        )
        val result = authService.login(LoginRequest("user2", "user123").toDto())
        val body = result.body()
        assertThat(result.isSuccessful).isTrue()
        assertThat(result.code()).isEqualTo(200)
        assertThat(body?.email).isEqualTo("test@example.com")
        assertThat(body?.userDetails?.name).isEqualTo("John Detail")
        assertThat(body?.token).isEqualTo("abc123")
    }

    @Test
    fun `login should return 401 when unauthorized`() = runTest {
        // Arrange: Enqueue a 401 Unauthorized response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(401)
                .setBody("""{ "detail": "Unauthorized" }""")
        )

        // Act: Call the login endpoint with invalid credentials
        val result = authService.login(LoginRequest("invalidUser", "wrongPassword").toDto()
        )

        // Assert
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.code()).isEqualTo(401)
        assertThat(result.errorBody()?.string()).contains("Unauthorized")
    }

}