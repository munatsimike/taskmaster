package com.teqie.taskmaster.ui.components.snackbar

import com.teqie.taskmaster.ui.model.MessageType


data class CustomSnackbarData(
    val message: String,
    val type: MessageType
)