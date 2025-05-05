package com.teqie.taskmaster.ui.uiState

import com.teqie.taskmaster.ui.model.UiMessage

abstract class UiState(
    open val isDropDownExpanded: Boolean,
    open val message: UiMessage,
)