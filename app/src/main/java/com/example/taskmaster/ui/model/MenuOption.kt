package com.example.taskmaster.ui.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class MenuOption(
    val content: @Composable (Modifier) -> Unit,
    val onClick: () -> Unit = {}
)