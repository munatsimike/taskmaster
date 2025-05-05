package com.teqie.taskmaster.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
    fabGradientColors: List<Color>,
    expanded: Boolean = true,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(47),
        shadowElevation = 4.dp,
        modifier = Modifier.height(45.dp),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .background(Brush.horizontalGradient(fabGradientColors))
        ) {
            ExtendedFloatingActionButton(
                onClick = onClick,
                icon = {
                    Icon(
                        imageVector = btnImage,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.White
                    )
                },
                text = {
                    Text(
                        text = buttonText,
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                containerColor = Color.Transparent,
                expanded = expanded,
                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp)
            )
        }
    }
}
