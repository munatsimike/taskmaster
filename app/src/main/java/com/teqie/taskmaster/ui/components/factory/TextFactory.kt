package com.teqie.taskmaster.ui.components.factory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teqie.taskmaster.R
import com.teqie.taskmaster.ui.model.ScreenType

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


    @Composable
    fun EmptyStateMessage(screen: ScreenType) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getEmptyStateMessage(screen),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
        }
    }

    @Composable
    fun getEmptyStateMessage(screen: ScreenType): String {
        return when (screen) {
            ScreenType.PROJECT -> "No team members assigned to this project"
            ScreenType.TEAM -> "No team members found"
            ScreenType.SCHEDULE -> "No tasks available"
            ScreenType.INVOICES -> "No invoices recorded"
            ScreenType.ORFI -> ""
            ScreenType.FOLDER -> ""
            ScreenType.GALLERY -> stringResource(R.string.empty_screen_text_images)
        }
    }

}


