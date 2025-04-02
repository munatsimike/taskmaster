package com.example.taskmaster.util.components.factory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.taskmaster.ui.theme.darkBlue

object ButtonFactory {

    @Composable
    fun PrimaryButton(
        buttonText: String,
        onButtonClick: () -> Unit,
        height: Int = 50,
        width: Float = 0.8f,
        modifier: Modifier = Modifier,
        containerColor: Color = darkBlue,
        contentColor: Color = Color.White
    ) {
        CustomButton(
            width = width,
            height = height,
            buttonText = buttonText,
            onButtonClick = onButtonClick,
            modifier = modifier,
            containerColor = containerColor,
            contentColor = contentColor
        )
    }

    @Composable
    private fun CustomButton(
        buttonText: String,
        modifier: Modifier = Modifier,
        containerColor: Color,
        contentColor: Color,
        height: Int,
        width: Float,
        borderColor: Color? = null,
        onButtonClick: () -> Unit,
    ) {
        Button(
            onClick = { onButtonClick() },
            modifier = Modifier
                .fillMaxWidth(width)
                .height(height.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(12.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor
            ),
            shape = RoundedCornerShape(12.dp),
            border = borderColor?.let {
                BorderStroke(2.dp, it) // Applies border only for secondary buttons
            }
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = contentColor
            )
        }
    }

    @Composable
    fun SecondaryButton(
        text: String,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        icon: Int? = null,
        borderColor: Color = darkBlue,
        textColor: Color = darkBlue,
        iconTint: Color = darkBlue,
        backgroundColor: Color = Color.White // Default to outlined style
    ) {
        Surface(
            modifier = modifier
                .height(36.dp)
                .border(
                    border = BorderStroke(1.dp, borderColor),
                    shape = RoundedCornerShape(12.dp)
                ),
            tonalElevation = if (backgroundColor == Color.Transparent) 0.dp else 5.dp, // Only elevate if filled
            shadowElevation = 2.dp,
            shape = RoundedCornerShape(12.dp),
            color = backgroundColor
        ) {
            TextButton(
                onClick = onClick,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    icon?.let {
                        Icon(
                            painter = rememberVectorPainter(ImageVector.vectorResource(id = it)),
                            contentDescription = null,
                            tint = iconTint
                        )
                    }
                    Text(
                        text = text,
                        color = textColor,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
