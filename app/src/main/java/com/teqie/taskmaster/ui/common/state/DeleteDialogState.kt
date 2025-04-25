package com.teqie.taskmaster.ui.common.state

data class DeleteDialogState(
    val selectedItem: String? = null,
    val selectedItemId: String? = null,
    val isVisible: Boolean = false
)