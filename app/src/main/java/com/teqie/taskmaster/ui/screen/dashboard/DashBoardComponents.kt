package com.teqie.taskmaster.ui.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DisplayCardTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun DisplaySubTitle(
    subTitle: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified
) {
    Text(
        text = subTitle,
        fontSize = 13.sp,
        color = color,
        modifier = Modifier
            .padding(bottom = 2.dp)
            .then(modifier)
    )
}

@Composable
fun DisplayLegend(
    firstBoxColor: Color,
    secondBoxColor: Color,
    firstBoxLabel: String,
    secondBoxLabel: String
) {
    // Legend for Spent and Balance
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        val textPadding = 4.dp
        val rowVerticalAlignment: Alignment.Vertical = Alignment.CenterVertically

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val fontSize = 12.sp
            Row(verticalAlignment = rowVerticalAlignment) {
                // Spent key
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(firstBoxColor)
                )
                Text(
                    text = firstBoxLabel,
                    fontSize = fontSize,
                    modifier = Modifier.padding(start = textPadding)
                )
            }

            Row(verticalAlignment = rowVerticalAlignment) {
                // Balance key
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(secondBoxColor)
                )
                Text(
                    text = secondBoxLabel,
                    fontSize = fontSize,
                    modifier = Modifier.padding(start = textPadding)
                )
            }

        }
    }
}