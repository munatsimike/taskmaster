package com.teqie.taskmaster.ui.common.snackbar

import com.teqie.taskmaster.ui.model.MessageType


data class CustomSnackbarData(
    val message: String,
    val type: MessageType
)