package com.teqie.taskmaster.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedLinearProgressBar(progress: Float) {
    // Use animateFloatAsState to animate the progress change
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec, label = ""
    )

    LinearProgressIndicator(
        color = MaterialTheme.colorScheme.tertiary,
        progress = { animatedProgress },
        modifier = Modifier
            .fillMaxWidth()
            .height(9.dp).background(MaterialTheme.colorScheme.tertiary),
    )
}