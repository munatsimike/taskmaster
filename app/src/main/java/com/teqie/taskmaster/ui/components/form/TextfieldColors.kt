package com.teqie.taskmaster.ui.components.form

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun customTxtFieldColors(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent, // Transparent background for outlined style
        unfocusedContainerColor = Color.Transparent,
        focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Color for outline when focused
        unfocusedIndicatorColor = Color(28, 146, 255)// Color for outline when unfocused
    )
}