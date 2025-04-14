package com.example.taskmaster.ui.common.factory

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

object TextFactory {

    @Composable
    fun TitleText(
        text: String,
        modifier: Modifier = Modifier,
        textColor: Color = MaterialTheme.colorScheme.onSurface
    ) {
        Text(
            text = text.replaceFirstChar { it.uppercaseChar() },
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            color = textColor,
            modifier = modifier
        )
    }

    @Composable
    fun SubtitleText(
        text: String,
        modifier: Modifier = Modifier,
        textDecoration: TextDecoration = TextDecoration.None,
        textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
            color = textColor,
            textDecoration = textDecoration,
            modifier = modifier
        )
    }

    @Composable
    fun BodyText(
        text: String,
        modifier: Modifier = Modifier,
        textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
            modifier = modifier
        )
    }

    @Composable
    fun CaptionText(
        text: String,
        modifier: Modifier = Modifier,
        textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = textColor,
            modifier = modifier
        )
    }

    @Composable
    fun LabelText(
        text: String,
        modifier: Modifier = Modifier,
        textColor: Color = MaterialTheme.colorScheme.primary
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
            color = textColor,
            modifier = modifier
        )
    }
}


