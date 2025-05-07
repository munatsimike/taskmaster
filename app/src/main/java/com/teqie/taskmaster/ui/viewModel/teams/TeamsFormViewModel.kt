package com.teqie.taskmaster.ui.viewModel.teams

import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.domain.model.user.CreateUserRequest
import com.teqie.taskmaster.domain.useCases.user.CreateUserUseCase
import com.teqie.taskmaster.domain.useCases.user.SyncTeamsToLocalDbUseCase
import com.teqie.taskmaster.ui.viewModel.BaseFormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsFormViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val syncTeamsToLocalDbUseCase: SyncTeamsToLocalDbUseCase

    ) : BaseFormViewModel() {
    override fun createFormState() {
    }

    override fun editFormState() {
    }

    override fun onIdChange(id: String) {
    }

    override fun clearForm() {
    }

      private val _createUserRequestState = MutableStateFlow(CreateUserRequest())
    val createUseRequestState: StateFlow<CreateUserRequest> = _createUserRequestState

    fun createAssignUser(projectId: String) {
        val newUser = _createUserRequestState.value
        if (!newUser.isValid()) {
            viewModelScope.launch {
                createUserUseCase(newUser.copy(projectId = projectId)).collect { response ->
                    handleResponse(response)
                }
            }
        }
    }

    fun onNameChange(name: String) {
        _createUserRequestState.update { it.copy(name = name) }
    }

    fun onEmailChange(email: String) {
        _createUserRequestState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _createUserRequestState.update { it.copy(password = password) }
    }

    fun onRoleChange(role: String) {
        _createUserRequestState.update { it.copy(role = role) }
    }

    fun onUsernameChange(username: String) {
        _createUserRequestState.update { it.copy(username = username) }
    }
}