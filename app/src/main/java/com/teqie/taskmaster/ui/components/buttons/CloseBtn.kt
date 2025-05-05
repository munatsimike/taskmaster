package com.teqie.taskmaster.ui.components.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.teqie.taskmaster.ui.components.imageDisplay.DisplayImageVectorIcon
import com.teqie.taskmaster.ui.theme.darkBlue

@Composable
fun CloseButton() {

    Surface(
        shadowElevation = 5.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.clickable {  }
            .padding(10.dp)
            .border(
            width = 1.dp,
            shape = RoundedCornerShape(10.dp),
            color = Color.White,
        )
    ) {
        DisplayImageVectorIcon(icon = Icons.Default.Close, tint = darkBlue, iconSize = 35)
    }
}