package com.teqie.taskmaster.ui.screen.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.teqie.taskmaster.R
import com.teqie.taskmaster.ui.components.CustomCard
import com.teqie.taskmaster.ui.theme.budgetPhaseColor
import com.teqie.taskmaster.util.formatCurrency

import kotlin.math.roundToInt

@Composable
fun BudgetCard(
    spent: Float,
    total: Float,
    cardBoarderColor: Color = budgetPhaseColor,
    oncCardClick: () -> Unit,
) {
    val size = (LocalConfiguration.current.screenWidthDp / 3).coerceIn(100, 200)
    CustomCard(onImageClick = oncCardClick, modifier = Modifier.fillMaxWidth(0.5f), cardBorderColor = cardBoarderColor) {
        Column(
            Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            DisplayCardTitle(title = "BudgetPhase Overview")
            Spacer(modifier = Modifier.height(4.dp))

            // Box for Pie Chart and Overlaid Labels
            Box(modifier = Modifier.size(size.dp)) {
                DisplayPieChart(spent, total, cardBoarderColor)
            }

            Spacer(modifier = Modifier.height(5.dp))
            DisplaySubTitle(subTitle = stringResource(id = R.string.total, formatCurrency(total.toDouble())))
            DisplayLegend(
                cardBoarderColor,
                Color.Blue,
                "Paid  ${formatCurrency(spent.toDouble())}",
                "Bal  ${formatCurrency((total - spent).toDouble())}"
            )
        }
    }
}

@Composable
private fun DisplayPieChart(spent: Float, total: Float, cardBoarderColor: Color) {
    // Normalize the spent value relative to the total
    val spentPercentage = (spent / total).coerceAtMost(1.0f) * 100f
    val remainingPercentage = 100f - spentPercentage

    val startAngle = 180f
    val sweepAngle = (spentPercentage / 100f) * 360f
    val remainingSweepAngle = 360f - sweepAngle

    // Pie chart using Canvas
    Canvas(modifier = Modifier.fillMaxSize()) {

        // Draw the spent arc (red)
        drawArc(
            color = cardBoarderColor,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = true
        )

        // Draw the remaining arc (blue)
        drawArc(
            color = Color.Blue,
            startAngle = startAngle + sweepAngle,
            sweepAngle = remainingSweepAngle,
            useCenter = true
        )
    }

    // Call DisplayArcLabels with dynamic percentages
    DisplayArcLabels(
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        remainingSweepAngle = remainingSweepAngle,
        spentPercentage = spentPercentage,
        remainingPercentage = remainingPercentage
    )
}

@Composable
private fun DisplayArcLabels(
    startAngle: Float,
    sweepAngle: Float,
    remainingSweepAngle: Float,
    spentPercentage: Float,      // Add dynamic spent percentage
    remainingPercentage: Float   // Add dynamic remaining percentage
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.minDimension / 2

        // Android paint for drawing text
        val textPaint = android.graphics.Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = 40f
            textAlign = android.graphics.Paint.Align.CENTER
        }

        // Calculate the position for the "Spent" label
        val spentAngle = startAngle + sweepAngle / 2
        val spentX =
            centerX + radius * 0.6f * kotlin.math.cos(Math.toRadians(spentAngle.toDouble()))
                .toFloat()
        val spentY =
            centerY + radius * 0.6f * kotlin.math.sin(Math.toRadians(spentAngle.toDouble()))
                .toFloat()

        // Calculate the position for the "Remaining" label
        val remainingAngle = startAngle + sweepAngle + remainingSweepAngle / 2
        val remainingX =
            centerX + radius * 0.6f * kotlin.math.cos(Math.toRadians(remainingAngle.toDouble()))
                .toFloat()
        val remainingY =
            centerY + radius * 0.6f * kotlin.math.sin(Math.toRadians(remainingAngle.toDouble()))
                .toFloat()

        if (!spentPercentage.isNaN()) {
            // Draw the dynamic text for "Spent" and "Remaining"
            drawContext.canvas.nativeCanvas.drawText(
                "${spentPercentage.roundToInt()}%",
                spentX,
                spentY,
                textPaint
            )
        }

        if (!remainingPercentage.isNaN()) {
            drawContext.canvas.nativeCanvas.drawText(
                "${remainingPercentage.roundToInt()}%",
                remainingX,
                remainingY,
                textPaint
            )
        }
    }
}