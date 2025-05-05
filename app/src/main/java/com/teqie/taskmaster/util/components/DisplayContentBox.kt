package com.teqie.taskmaster.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DisplayContentBox(
    label: String? = null,
    shape: Shape = RoundedCornerShape(7.dp),// Optional label
    content: String,
    padding: Int = 12,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.wrapContentSize()) {
        // Only display the label if it's not null or empty
        label?.let {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = it,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp)) // Space between label and box
        }

        // Box to display content with customizable colors
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.then(modifier)
                .widthIn(70.dp)
                .clip(shape = shape)
                .background(backgroundColor)
                .border(1.dp, borderColor, shape)
                .padding(padding.dp)
        ) {
            Text(
                text = content,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = contentColor
            )
        }
    }
}
