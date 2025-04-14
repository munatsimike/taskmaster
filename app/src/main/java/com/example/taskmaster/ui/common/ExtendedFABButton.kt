package com.example.taskmaster.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ExtendedFABButton(
    buttonText: String,
    btnImage: ImageVector,
    fabGradientColors: List<Color>, // Add gradient colors as a parameter
    expanded: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(fabGradientColors),
                shape = RoundedCornerShape(47) // Match FAB shape
            )
            .height(45.dp)
            .padding(horizontal = 2.dp)
    ) {
        ExtendedFloatingActionButton(
            onClick = { onClick() },
            icon = {
                Icon(
                    imageVector = btnImage,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp), // Reduced icon size
                    tint = Color.White
                )
            },
            text = {
                Text(
                    text = buttonText,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall // Reduced text size
                )
            },
            containerColor = Color.Transparent, // Use transparent as the background is handled by the gradient
            expanded = expanded,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 4.dp, // Slightly reduced elevation
                pressedElevation = 8.dp
            )
        )
    }
}
