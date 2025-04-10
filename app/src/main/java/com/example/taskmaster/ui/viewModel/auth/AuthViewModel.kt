package com.example.taskmaster.ui.viewModel.auth


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.data.local.preferences.AccessToken
import com.example.taskmaster.domain.LoginRequest
import com.example.taskmaster.domain.model.User
import com.example.taskmaster.domain.useCases.auth.GetAccessTokenUseCaseImp
import com.example.taskmaster.domain.useCases.auth.LoginUseCase
import com.example.taskmaster.domain.useCases.auth.LogoutUseCaseImp
import com.example.taskmaster.ui.common.state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    private  val logoutUseCase: LogoutUseCaseImp

) : ViewModel() {
    private val _uiState: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState())

    init {
        checkAccessToken()
    }

    val uiState: StateFlow<AuthUiState> = _uiState
    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible

    fun togglePasswordVisibility(){
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
            val loginRequest =
                LoginRequest(_uiState.value.username.trim(), _uiState.value.password.trim())
            if (loginRequest.isValid()) {
                val result = loginUseCase(loginRequest)
                if (result.isSuccess) {
                    // Await token collection to ensure it updates before proceeding
                    checkAccessToken()
                    // Update state only after token is confirmed
                    upDateUiState(result)
                } else {
                    _uiState.update { it.copy(error = "login Failed") }
                }
            } else {
                _uiState.update { it.copy(error = "Username or password cannot be blank") }
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            logoutUseCase.logout()
            checkAccessToken()
        }
    }

    private fun upDateUiState(result: Result<User>) {
        if (result.isSuccess) {
            _uiState.update { it.copy(isSuccessful = true) }
        } else {
            _uiState.update { it.copy(error = "login Failed") }
        }
        clearLoginForm()
    }

    private fun clearLoginForm(){
        _uiState.update { it.copy(username = "", password = "", error = "") }
    }

    // Check access token availability and update the state
    private fun checkAccessToken() {
        viewModelScope.launch {
            try {
                getAccessTokenUseCase().collectLatest { token:AccessToken ->
                    _uiState.update {
                        it.copy(hasToken = token.isNotEmpty())
                    }
                }
            } catch (e: CancellationException) {
                Timber.i("token view model", "Coroutine was cancelled in ViewModel", e)
            } catch (e: Exception) {
                Timber.i("token error", e.message.toString())
            }
        }
    }
}
