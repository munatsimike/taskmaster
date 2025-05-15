package com.teqie.taskmaster.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teqie.taskmaster.util.components.DelayedAction

@Composable
fun Tooltip(
    text: String,
    icon: ImageVector = Icons.Outlined.Info,
    backgroundColor: Color = Color(64, 64, 64, 255),
    textColor: Color = Color.White,
    iconColor: Color = MaterialTheme.colorScheme.primary,
    onDismiss: () -> Unit,
) {
    var isVisible by remember { mutableStateOf(true) }

    val tooltipModifier = Modifier
        .fillMaxWidth(0.98f)
        .background(backgroundColor, shape = RoundedCornerShape(8.dp))

    DelayedAction(key = Unit) {
        isVisible = false
        onDismiss()
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)) + expandVertically(),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) + shrinkVertically()
    ) {
        Surface(
            modifier = tooltipModifier
                .clickable { onDismiss() },
            shadowElevation = 2.dp,
        ) {
            Row(
                modifier = tooltipModifier
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(40.dp),
                    imageVector = icon,
                    tint = iconColor,
                    contentDescription = "Tooltip Icon"
                )
                Text(
                    text = text,
                    fontSize = 14.sp,
                    color = textColor,
                    letterSpacing = 0.5.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}
