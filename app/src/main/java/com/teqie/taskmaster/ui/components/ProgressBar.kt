package com.teqie.taskmaster.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun DisplayProgressBar(
    message: String = "",
    isAnimating: Boolean = false
) {
    // Animating text effect
    var animatedMessage by remember { mutableStateOf("") }

    // Display the loading UI
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator() // A simple Material 3 circular progress bar
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = animatedMessage,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp),
            color = Color(0xFF130F08)
        )
    }

    LaunchedEffect(isAnimating) {
        while (isAnimating) {
            animatedMessage = if (animatedMessage.isNotBlank()) {
                ""
            } else {
                message
            }
            delay(1000L)
        }
    }
}

