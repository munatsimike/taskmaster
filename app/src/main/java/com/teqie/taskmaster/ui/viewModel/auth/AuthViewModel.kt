package com.teqie.taskmaster.ui.viewModel.auth


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.data.local.preferences.AccessToken
import com.teqie.taskmaster.domain.LoginRequest
import com.teqie.taskmaster.domain.model.auth.User
import com.teqie.taskmaster.domain.useCases.auth.GetAccessTokenUseCaseImp
import com.teqie.taskmaster.domain.useCases.auth.LoginUseCase
import com.teqie.taskmaster.domain.useCases.auth.LogoutUseCaseImp
import com.teqie.taskmaster.domain.useCases.auth.ValidateTokenUseCase
import com.teqie.taskmaster.ui.components.state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

/**AuthViewModel is responsible for managing the UI state of the login screen.
 *
 * This ViewModel interacts with the LoginUseCaseImp to perform the login operation,
 * updates the UI state based on user input, and handles the results of login attempts.
 * It exposes a StateFlow for observing the UI state in the UI layer, allowing the UI
 * to react to changes.
 *
 **/

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCaseImp,
    private val logoutUseCase: LogoutUseCaseImp,
    private val validateTokenUseCase: ValidateTokenUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible

    init {
        checkAccessToken()
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !isPasswordVisible.value
    }

    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun login() {
        viewModelScope.launch {
            val loginRequest = LoginRequest(
                _uiState.value.username.trim(),
                _uiState.value.password.trim()
            )
            if (loginRequest.isValid()) {
                val result = loginUseCase(loginRequest)
                if (result.isSuccess) {
                    checkAccessToken()
                    updateUiState(result)
                } else {
                    _uiState.update { it.copy(error = "Login failed") }
                }
            } else {
                _uiState.update { it.copy(error = "Username or password cannot be blank") }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.logout()
            checkAccessToken()
        }
    }

    private fun updateUiState(result: Result<User>) {
        if (result.isSuccess) {
            _uiState.update { it.copy(isSuccessful = true) }
        } else {
            _uiState.update { it.copy(error = "Login failed") }
        }
        clearLoginForm()
    }

    private fun clearLoginForm() {
        _uiState.update { it.copy(username = "", password = "", error = "") }
    }

    private fun checkAccessToken() {
        viewModelScope.launch {
            try {
                getAccessTokenUseCase().collectLatest { token: AccessToken ->
                    val hasToken = token.isNotEmpty()

                    if (hasToken) {
                        val isValid = validateTokenSafely()
                        if (!isValid) {
                            logoutUseCase.logout()
                            _uiState.update { it.copy(hasToken = false) }
                        } else {
                            _uiState.update { it.copy(hasToken = true) }
                        }
                    } else {
                        _uiState.update { it.copy(hasToken = false) }
                    }
                }
            } catch (e: CancellationException) {
                Timber.i("token view model", "Coroutine was cancelled in ViewModel", e)
            } catch (e: Exception) {
                Timber.i("token error", e.message.toString())
            }
        }
    }

    private suspend fun validateTokenSafely(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = validateTokenUseCase() // /users/me

                if (response.isSuccessful) {
                    true // token is valid
                } else {
                    response.code() != 401 // token is valid if not 401
                }
            } catch (e: HttpException) {
                e.code() != 401
            } catch (e: Exception) {
                false // network error or unknown → treat as invalid
            }
        }
    }
}