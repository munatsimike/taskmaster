package com.teqie.taskmaster.util.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Composable
fun <T> DelayedAction(
    key: T,
    delayTime: Long = 8000L,
    action: () -> Unit
) {
    LaunchedEffect(key) {
        delay(delayTime) // Convert delayTime to milliseconds
        action()
    }
}