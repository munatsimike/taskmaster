package com.teqie.taskmaster.ui.components.state

data class DeleteDialogState(
    val selectedItem: String? = null,
    val selectedItemId: String? = null,
    val isVisible: Boolean = false
)