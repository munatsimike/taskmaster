package com.teqie.taskmaster.ui.components.imageDisplay

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun DisplayImageVectorIcon(
    icon: ImageVector,
    iconSize: Int = 16,
    tint: Color = MaterialTheme.colorScheme.onBackground,
    description: String? = null,
    modifier: Modifier = Modifier
) {
    Icon(
        modifier = Modifier
            .size(iconSize.dp)
            .then(modifier),
        imageVector = icon,
        tint = tint ,
        contentDescription = description
    )
    Spacer(modifier = Modifier.width(4.dp))
}