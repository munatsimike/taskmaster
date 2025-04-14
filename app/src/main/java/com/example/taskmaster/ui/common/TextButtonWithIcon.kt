package com.example.taskmaster.ui.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.taskmaster.ui.common.imageDisplay.DisplayImageVectorIcon

@Composable
fun TextButtonWithIcon(
    btnText: String,
    icon: ImageVector,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    secondIconIsVisible: Boolean = false,
    iconTint: Color = Color.Unspecified,
    secondIcon: ImageVector? = null,
    secondIconTint: Color? = null
) {
    TextButton(
        modifier = modifier,
        onClick = { onClick() },
        enabled = enabled,
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            DisplayImageVectorIcon(
                icon = icon,
                tint = if (secondIconIsVisible) iconTint.copy(alpha = 0.5f) else iconTint
            )
            Text(text = btnText)
            Spacer(modifier = Modifier.width(6.dp))
            if (secondIconIsVisible)
                secondIcon?.let {
                    if (secondIconTint != null) {
                        DisplayImageVectorIcon(icon = it, tint = secondIconTint, iconSize = 18)
                    }
                }
        }
    }
}