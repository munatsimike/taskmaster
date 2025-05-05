package com.teqie.taskmaster.util.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun KeyboardUpDownArrowIcon(isExpanded: Boolean) {
        Icon(
            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp
            else Icons.Default.KeyboardArrowDown,
            contentDescription = if (isExpanded) "Collapse" else "Expand",
        )
}