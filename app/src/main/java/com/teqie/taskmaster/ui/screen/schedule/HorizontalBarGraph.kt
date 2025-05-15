package com.example.taskflow.ui.screen.schedule

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.taskflow.ui.util.components.TextFactory.BodyText

@Composable
fun HorizontalBarGraph(
    progress: Float,
    progressBarColor: Color,
    progressBarText: String = ""
) {
    val gradient = Brush.radialGradient(
        colors = listOf(progressBarColor, progressBarColor)
    )
    var recWidth = 0f
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(24.dp)
            .background(Color(0xFFE0E0E0), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            recWidth = size.width
            val barWidth = recWidth * (if (progress > 1f) 1f else progress)
            drawRoundRect(
                brush = gradient,
                topLeft = Offset(0f, 0f),
                size = Size(barWidth, size.height),
                cornerRadius = CornerRadius(10.dp.toPx())
            )
        }
        // Display the progress as a  percentage inside the bar
        DisplayProgressPercentage(progress, recWidth, progressBarText)
    }
}
@Composable
private fun DisplayProgressPercentage(
    progress: Float,
    recWidth: Float,
    progressBarText: String
) {
    // Display Percentage Text inside the bar along with total duration
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .offset(x = ((recWidth * progress) - 40).dp.coerceAtLeast(8.dp)) // Offset the text within the bar
    ) {
        BodyText(
            text = progressBarText
        )

    }
}

@Composable
fun DisplayCheckCircle(progress: Float) {
    if (progress == 1f) {
        Box(
            modifier = Modifier
                .padding(3.dp)
                .clip(CircleShape)
                .background(Color.White)
        ) {
            Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = "Completed",
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
