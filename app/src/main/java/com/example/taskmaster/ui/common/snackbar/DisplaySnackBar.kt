package com.example.taskmaster.ui.common.snackbar

import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.example.taskmaster.ui.model.MessageType
import com.example.taskmaster.ui.model.UiMessage
import kotlinx.coroutines.launch

@Composable
fun DisplaySnackBar(
    uiMessage: UiMessage,
    customSnackbarHostState: CustomSnackbarHostState,
    onDismissed: () -> Unit
) {
    val message = uiMessage.success ?: uiMessage.error
    val messageType = when {
        uiMessage.success != null && uiMessage.messageType == MessageType.SUCCESS -> MessageType.SUCCESS
        uiMessage.error != null && uiMessage.messageType == MessageType.ERROR -> MessageType.ERROR
        else -> null
    }

    // Trigger snackbar if message and type are valid
    if (message != null && messageType != null) {
        LaunchSnackBar(
            messageId = uiMessage.id,
            message = message,
            messageType = messageType,
            customSnackbarHostState = customSnackbarHostState,
            onDismissed = onDismissed
        )
    }
}

@Composable
private fun LaunchSnackBar(
    messageId: Int,
    message: String,
    messageType: MessageType,
    customSnackbarHostState: CustomSnackbarHostState,
    onDismissed: () -> Unit
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(messageId) {
        scope.launch {
            val result = customSnackbarHostState.showSnackbar(
                CustomSnackbarData(
                    message = message,
                    type = messageType
                )

            )
            if (result == SnackbarResult.Dismissed) {
                onDismissed()
            }
        }
    }
}