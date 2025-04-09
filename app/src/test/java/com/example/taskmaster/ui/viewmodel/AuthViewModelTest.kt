package com.example.taskmaster.ui.viewmodel

import com.example.taskmaster.data.local.preferences.AccessToken
import com.example.taskmaster.domain.LoginRequest
import com.example.taskmaster.domain.model.User
import com.example.taskmaster.domain.useCases.auth.GetAccessTokenUseCaseImp
import com.example.taskmaster.domain.useCases.auth.LoginUseCaseImp
import com.example.taskmaster.domain.useCases.auth.LogoutUseCaseImp
import com.example.taskmaster.ui.viewModel.auth.AuthViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    private val loginUseCaseImp = mockk<LoginUseCaseImp>()
    private val getAccessTokenUseCase = mockk<GetAccessTokenUseCaseImp>()
    private val logoutUseCase = mockk<LogoutUseCaseImp>()
    private lateinit var authViewModel: AuthViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        authViewModel = AuthViewModel(loginUseCaseImp, getAccessTokenUseCase, logoutUseCase)
    }

    @Test
    fun `login updates state on success`() = runTest {
        val mockUser = User("avatar", "Test", 0, "abc123")

        coEvery { loginUseCaseImp(any()) } returns Result.success(mockUser)
        coEvery { getAccessTokenUseCase() } returns flowOf(AccessToken(value = "abc123"))

        authViewModel.onUsernameChange("user")
        authViewModel.onPasswordChange("pass")
        authViewModel.login()

        advanceUntilIdle()

        val state = authViewModel.uiState.value
        assertThat(state.isSuccessful).isTrue()
        assertThat(state.hasToken).isTrue()
    }

    @Test
    fun `login success updates isSuccessful and hasToken`() = runTest {
        val user = User(
            name = "John",
            avatar = "avatar.png",
            isSuperUser = 0,
            token = "abc123"
        )

        coEvery { loginUseCaseImp(any()) } returns Result.success(user)
        coEvery { getAccessTokenUseCase() } returns flowOf(AccessToken(value = "abc123"))

        authViewModel = AuthViewModel(loginUseCaseImp, getAccessTokenUseCase, logoutUseCase)
        authViewModel.onUsernameChange("john")
        authViewModel.onPasswordChange("pass")
        authViewModel.login()

        advanceUntilIdle()

        val state = authViewModel.uiState.value
        assertThat(state.isSuccessful).isTrue()
        assertThat(state.hasToken).isTrue()
    }

    @Test
    fun `empty token results in hasToken false`() = runTest {
        coEvery { getAccessTokenUseCase() } returns flowOf(AccessToken(value = ""))

        authViewModel = AuthViewModel(loginUseCaseImp, getAccessTokenUseCase, logoutUseCase)

        advanceUntilIdle()

        val state = authViewModel.uiState.value
        assertThat(state.hasToken).isFalse()
    }

    @Test
    fun `logout clears token`() = runTest {
        coEvery { logoutUseCase.logout() } just Runs
        coEvery { getAccessTokenUseCase() } returns flowOf(AccessToken(value = ""))

        authViewModel = AuthViewModel(loginUseCaseImp, getAccessTokenUseCase, logoutUseCase)
        authViewModel.logout()

        advanceUntilIdle()

        val state = authViewModel.uiState.value
        assertThat(state.hasToken).isFalse()
    }

    @Test
    fun `login with blank username or password shows validation error`() = runTest {
        authViewModel = AuthViewModel(loginUseCaseImp, getAccessTokenUseCase, logoutUseCase)

        authViewModel.onUsernameChange("") //  blank username
        authViewModel.onPasswordChange("") //  blank password
        authViewModel.login()

        advanceUntilIdle()
        val state = authViewModel.uiState.value
        assertThat(state.error).isEqualTo("Username or password cannot be blank")
    }

    @Test
    fun `login failure shows error message`() = runTest {
        val loginRequest = LoginRequest("user", "pass")
        coEvery { loginUseCaseImp(loginRequest) } returns Result.failure(Exception("invalid"))

        authViewModel = AuthViewModel(loginUseCaseImp, getAccessTokenUseCase, logoutUseCase)
        authViewModel.onUsernameChange("user")
        authViewModel.onPasswordChange("pass")
        authViewModel.login()

        advanceUntilIdle()

        val state = authViewModel.uiState.value
        assertThat(state.error).isEqualTo("login Failed")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
