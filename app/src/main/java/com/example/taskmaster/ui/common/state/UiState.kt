package com.example.taskmaster.ui.common.state

import com.example.taskmaster.ui.model.UiMessage

abstract class UiState(
    open val isDropDownExpanded: Boolean,
    open val message: UiMessage,
)