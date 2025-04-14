package com.example.taskmaster.ui.common.snackbar

import com.example.taskmaster.ui.model.MessageType


data class CustomSnackbarData(
    val message: String,
    val type: MessageType
)