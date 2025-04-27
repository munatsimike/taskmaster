package com.teqie.taskmaster.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.ui.common.state.CommonUiState
import com.teqie.taskmaster.ui.common.state.FormState
import com.teqie.taskmaster.ui.model.MessageType
import com.teqie.taskmaster.ui.model.ResponseMessage
import com.teqie.taskmaster.ui.model.UiMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class ProjectViewModel : ViewModel() {

    protected val _screenState = MutableStateFlow(CommonUiState())
    val screenState: StateFlow<CommonUiState> = _screenState

    open fun toggleIsSuccessful() {
        _screenState.update { it.copy(isSuccessful = !it.isSuccessful) }
    }

    fun triggerDataFetch() {
        _screenState.update { it.copy(triggerFetch = it.triggerFetch + 1) }
    }

    fun setBottomSheetDescription(description: String?) {
        _screenState.update { it.copy(bottomSheetState = it.bottomSheetState.copy(content = description)) }
    }

    fun showBottomSheetModal() {
        _screenState.update { it.copy(bottomSheetState = it.bottomSheetState.copy(isVisible = true)) }
    }

    fun hideBottomSheetModal() {
        _screenState.update { it.copy(bottomSheetState = it.bottomSheetState.copy(isVisible = false)) }
    }


    fun showTooltip() {
        _screenState.update { it.copy(showToolTip = true) }
    }

    fun hideTooltip() {
        _screenState.update { it.copy(showToolTip = false) }
    }

    fun clearUiScreenStateMessage(uiScreenState: CommonUiState) {
        uiScreenState.takeIf { it.hasMessage() }?.let {
            _screenState.update { current -> current.copy(message = UiMessage()) }
        }
    }

    protected fun processApiMessage(
        message: Resource<ResponseMessage>,
        setupUiMessage: (MessageType, String) -> Unit
    ) {
        viewModelScope.launch {
            when (message) {
                is Resource.Error -> {
                    val msg = message.exception.localizedMessage ?: "An unexpected error occurred"
                    setupUiMessage(MessageType.ERROR, msg)
                }

                is Resource.Failure -> {
                    message.errorBody?.let { setupUiMessage(MessageType.ERROR, it) }
                    "Error ${message.code}: ${message.errorBody}"
                }

                Resource.Loading -> {}

                is Resource.Success -> {
                    setupUiMessage(MessageType.SUCCESS, message.data.message)
                    toggleIsSuccessful()
                }
            }
        }
    }

    open fun updateUiMessage(messageType: MessageType, message: String?) {
        _screenState.update {
            it.copy(
                message = it.message.copy(
                    messageType = messageType,
                    success = if (messageType.isSuccess()) message else null,
                    error = if (messageType.isError()) message else null,
                )
            )
        }
    }

    fun handleActions(
        uiScreenState: CommonUiState,
        formState: FormState,
        toggleFormSubmitted: () -> Unit
    ) {
        if (uiScreenState.isSuccessful || (!formState.isVisible && formState.isSubmitted)) {
            if (formState.isSubmitted) {
                toggleFormSubmitted()
            }
            if (uiScreenState.isSuccessful) {
                toggleIsSuccessful()
            }
            triggerDataFetch()
        }
    }

    fun getServerResponseMsg(formState: FormState, uiScreenState: CommonUiState): UiMessage {
        return when {
            !formState.isVisible && formState.hasMessage() -> formState.message
            uiScreenState.hasMessage() -> uiScreenState.message
            else -> UiMessage()
        }
    }

    open fun handleResponse(response: Resource<ResponseMessage>) {
        processApiMessage(response) { messageType: MessageType, message: String ->
            updateUiMessage(messageType, message)
        }
    }

    // Helper extension function to check if a message is present
    fun FormState.hasMessage(): Boolean {
        return message.error != null || message.success != null
    }

    private fun CommonUiState.hasMessage(): Boolean {
        return message.error != null || message.success != null
    }

    fun MessageType.isSuccess(): Boolean {
        return this == MessageType.SUCCESS
    }

    fun MessageType.isError(): Boolean {
        return this == MessageType.ERROR
    }

    fun clearServerResponseMessage() {
        _screenState.update { it.copy(message = it.message.copy(success = null)) }
    }

    private fun resetScreenState() {
        _screenState.value = CommonUiState()
    }
}