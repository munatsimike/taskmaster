package com.teqie.taskmaster.util.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AnimateContentVisibility(
    isVisible: Boolean,
    onHide: () -> Unit,
    content: @Composable () -> Unit
) {
    // Animated visibility of the expanded content
    AnimatedVisibility(visible = isVisible) {
        Column(horizontalAlignment = Alignment.Start) {
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
    DelayedAction(key = isVisible, delayTime = 20000) {
        onHide()
    }
}
