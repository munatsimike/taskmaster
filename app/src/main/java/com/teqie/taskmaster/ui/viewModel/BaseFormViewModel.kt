package com.teqie.taskmaster.ui.viewModel

import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.ui.components.state.CommonUiState
import com.teqie.taskmaster.ui.components.state.FormState
import com.teqie.taskmaster.ui.model.MessageType
import com.teqie.taskmaster.ui.model.ResponseMessage
import com.teqie.taskmaster.ui.model.UiMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class BaseFormViewModel : BaseScreenViewModel() {

    protected val _uiFormState = MutableStateFlow(FormState())
    val uiFormState: StateFlow<FormState> = _uiFormState

    fun expandDropDownMenu() {
        _uiFormState.update { it.copy(isDropDownExpanded = true) }
    }

    fun collapseDropDownMenu() {
        _uiFormState.update { it.copy(isDropDownExpanded = false) }
    }

    fun showForm() {
        _uiFormState.update { it.copy(isVisible = true) }
    }

    fun closeForm() {
        _uiFormState.update { it.copy(isVisible = false) }
    }

    fun clearUiFormState() {
        _uiFormState.value = FormState()
    }

    open fun startEditing() {
        _uiFormState.update { it.copy(isEditing = true) }
    }

    open fun stopEditing() {
        _uiFormState.update { it.copy(isEditing = false) }
    }

    fun toggleIsFormSubmitted() {
        _uiFormState.update { it.copy(isSubmitted = !it.isSubmitted) }
    }

    fun clearUiMessage(formState: FormState, commonUiState: CommonUiState) {
        clearFormStateUiMessage(formState)
        clearUiScreenStateMessage(commonUiState)
    }

    fun clearFormStateUiMessage(formState: FormState) {
        formState.takeIf { it.hasMessage() }?.let {
            _uiFormState.update { current -> current.copy(message = UiMessage()) }
        }
    }

    override fun updateUiMessage(messageType: MessageType, message: String?) {
        _uiFormState.update {
            it.copy(
                message = it.message.copy(
                    messageType = messageType,
                    success = if (messageType.isSuccess()) message else null,
                    error = if (messageType.isError()) message else null,
                )
            )
        }
    }

    override fun handleResponse(response: Resource<ResponseMessage>) {
        processApiMessage(response) { messageType: MessageType, message: String ->
            updateUiMessage(messageType, message)
        }
    }

    fun handleSubmitBtnClick() {
        if (uiFormState.value.isEditing) {
            stopEditing()
        }
        toggleIsFormSubmitted()
        closeForm()
        clearForm()
    }

    fun showEditForm() {
        startEditing()                   // Mark editing as active
        showForm()                       // Display the form
    }

    abstract fun createFormState()
    abstract fun editFormState()
    abstract fun onIdChange(id: String)
    abstract fun clearForm()

}