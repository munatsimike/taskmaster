package com.teqie.taskmaster.ui.common.state

import com.teqie.taskmaster.ui.model.UiMessage

abstract class UiState(
    open val isDropDownExpanded: Boolean,
    open val message: UiMessage,
)