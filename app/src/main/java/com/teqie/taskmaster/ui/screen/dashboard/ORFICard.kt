package com.teqie.taskmaster.ui.screen.dashboard

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.teqie.taskmaster.domain.model.Totals
import com.teqie.taskmaster.ui.common.CustomCard
import com.teqie.taskmaster.ui.theme.orfiColor


@Composable
fun DonutChart(
    totals: Totals,
    innerText: String,
    cardBorderColor: Color = orfiColor,
    onCardClick: () -> Unit
) {
    CustomCard(onImageClick = onCardClick) {
        Column(
            Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val resolvedOrfi = totals.resolvedOrfis
            DisplayCardTitle(title = "ORFI Overview")
            Spacer(modifier = Modifier.height(20.dp))
            // color to shed the resolved or completed part of the chart and legend box
            DrawChart(totals, innerText, cardBorderColor)
            Spacer(modifier = Modifier.height(20.dp))
            DisplayLegend(
                cardBorderColor,
                Color.Gray,
                "Resolved  $resolvedOrfi",
                "Left  ${totals.totalOrfis - resolvedOrfi}"
            )
        }
    }
}

@Composable
private fun DrawChart(totals: Totals, innerText: String, resolvedColor: Color) {
    Canvas(modifier = Modifier.size(130.dp)) {
        var startAngle = 180f // Start from the top of the circle
        val donutThickness = 70f // Thickness of the donut
        val center = Offset(size.width / 2, size.height / 2)

        // Calculate the percentage of resolved ORFIs out of the total
        val resolvedPercentage =
            totals.resolvedOrfis.toFloat() / totals.totalOrfis.toFloat()

        // Calculate the sweep angle for resolved ORFIs
        val resolvedSweepAngle = resolvedPercentage * 360f

        // Draw the resolved (green) segment
        drawArc(
            color = resolvedColor,
            startAngle = startAngle,
            sweepAngle = resolvedSweepAngle,
            useCenter = false,
            style = Stroke(width = donutThickness, cap = StrokeCap.Butt)
        )

        // Update the start angle for the next segment
        startAngle += resolvedSweepAngle

        // Draw the unresolved (gray) segment, which is the remaining part of the donut
        val unresolvedSweepAngle = 360f - resolvedSweepAngle
        drawArc(
            color = Color.Gray,
            startAngle = startAngle,
            sweepAngle = unresolvedSweepAngle,
            useCenter = false,
            style = Stroke(width = donutThickness, cap = StrokeCap.Butt)
        )

        // Draw centered text inside the donut
        drawCenteredText(
            canvas = drawContext.canvas.nativeCanvas,
            text = "Request",
            center = center,
            yOffset = -25f, // Adjust offset for the first line
            textSize = 30f
        )

        drawCenteredText(
            canvas = drawContext.canvas.nativeCanvas,
            text = innerText,
            center = center,
            yOffset = 25f, // Adjust offset for the second line
            textSize = 40f,
            isBold = true
        )
    }
}

private fun drawCenteredText(
    canvas: android.graphics.Canvas,
    text: String,
    center: Offset,
    yOffset: Float = 0f,
    textSize: Float = 40f,
    isBold: Boolean = false
) {
    val textPaint = Paint().apply {
        color = android.graphics.Color.BLACK
        textAlign = android.graphics.Paint.Align.CENTER
        this.textSize = textSize
        isAntiAlias = true

        if (isBold) {
            typeface = android.graphics.Typeface.create(
                android.graphics.Typeface.DEFAULT,
                android.graphics.Typeface.BOLD
            )
        }
    }
    // Draw the text centered at the provided position with the specified yOffset
    canvas.drawText(
        text,
        center.x,
        center.y + yOffset - (textPaint.descent() + textPaint.ascent()) / 2,
        textPaint
    )
}
