package com.teqie.taskmaster.ui.common.factory

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import com.teqie.taskmaster.ui.theme.gradientEnd
import com.teqie.taskmaster.ui.theme.gradientStart

object AnimationFactory {
    @Composable
    fun rememberGradientTransition(
        colorStart: Color,
        colorEnd: Color,
        durationMillis: Int = 2000
    ): State<Color> {
        val transition = rememberInfiniteTransition(label = "Gradient Transition")
        return transition.animateColor(
            initialValue = colorStart,
            targetValue = colorEnd,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = durationMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "Gradient Animation"
        )
    }

    @Composable
    fun AnimatedColor(
        initialValue: Color = gradientStart,
        targetValue: Color = gradientEnd,
        duration: Int = 18000
    ): State<Color> {
        val infiniteTransition = rememberInfiniteTransition()

        return infiniteTransition.animateColor(
            initialValue = initialValue,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = duration),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    @Composable
    fun rememberFadeInOutAnimation(
        initialValue: Float = 0f,
        targetValue: Float = 1f,
        durationMillis: Int = 4500
    ): State<Float> {
        val transition = rememberInfiniteTransition(label = "Fade Transition")
        return transition.animateFloat(
            initialValue = initialValue,
            targetValue =targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = durationMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "Fade Animation"
        )
    }

    @Composable
    fun rememberPulseAnimation(
        initialScale: Float = 1f,
        targetScale: Float = 3.1f,
        durationMillis: Int = 1000
    ): State<Float> {
        val transition = rememberInfiniteTransition(label = "Pulse Transition")
        return transition.animateFloat(
            initialValue = initialScale,
            targetValue = targetScale,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = durationMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "Pulse Animation"
        )
    }
}
