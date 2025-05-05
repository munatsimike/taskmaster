package com.teqie.taskmaster.ui.components.state

import com.teqie.taskmaster.ui.model.UiMessage

data class FormState(
    val isVisible: Boolean = false,
    val isEditing: Boolean = false,
    val isSubmitted: Boolean = false,
    val formValidationErrors: Map<String, String> = emptyMap(),
    override val isDropDownExpanded: Boolean = false,
    override val message: UiMessage = UiMessage()
) : UiState(isDropDownExpanded, message)