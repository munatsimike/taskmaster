package com.teqie.taskmaster.ui.viewModel.orfi

import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.domain.useCases.orfi.CreateOrfiUseCase
import com.teqie.taskmaster.domain.useCases.orfi.UpdateOrfiUseCase
import com.teqie.taskmaster.ui.model.MessageType
import com.teqie.taskmaster.ui.model.orfi.Orfi
import com.teqie.taskmaster.ui.viewModel.BaseFormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ORFIFormViewModel @Inject constructor(
    private val createORFIUseCase: CreateOrfiUseCase,
    private val updateORFIUsecase: UpdateOrfiUseCase,
) :
    BaseFormViewModel() {

    private val _orfiFormState = MutableStateFlow(Orfi())
    val orfiFormState: StateFlow<Orfi> = _orfiFormState

    override fun createFormState() {
        viewModelScope.launch {
            createORFIUseCase(_orfiFormState.value).collect {
                handleResponse(it)
            }
            handleSubmitBtnClick()
        }
    }

    fun handleOrfiEditRequest(orfi: Orfi) {
        onOrfiChange(orfi)
        showEditForm()
    }

    private fun onOrfiChange(orfi: Orfi) {
        _orfiFormState.value = orfi
    }

    override fun editFormState() {
        viewModelScope.launch {
            updateORFIUsecase(_orfiFormState.value).collect {
                processApiMessage(it){ messageType: MessageType, message: String ->
                    updateUiMessage(messageType, message)
                }
            }
            handleSubmitBtnClick()
        }
    }

    override fun onIdChange(id: String) {
        _orfiFormState.update { it.copy(projectId = id) }
    }

    fun handleFormDismiss() {
        if (_uiFormState.value.isEditing){
            stopEditing()
        }
        clearUiFormState()
    }

    // clear form data
    override fun clearForm() {
        _orfiFormState.value = Orfi()
    }

    private fun onAssignedUserChange(user: String) {
        _orfiFormState.update { it.copy(assignedUserName = user) }
    }

    private fun onAssignedUserIdChange(userId: String) {
        _orfiFormState.update { it.copy(assignedTo = userId) }
    }

    fun onSelectedUser(userName: String, userId: String) {
        onAssignedUserChange(userName)
        onAssignedUserIdChange(userId)
        this.collapseDropDownMenu()
    }

    fun onDueDateChange(date: String) {
        _orfiFormState.update { it.copy(dueDate = date) }
    }

    fun onDateChange(date: String) {
        _orfiFormState.update { it.copy(updatedAt = date) }
    }

    fun onResolvedChange(isResolved: Boolean) {
        _orfiFormState.update { it.copy(resolved = !isResolved) }
    }

    fun onQuestionChange(question: String) {
        _orfiFormState.update { it.copy(question = question) }
    }
}