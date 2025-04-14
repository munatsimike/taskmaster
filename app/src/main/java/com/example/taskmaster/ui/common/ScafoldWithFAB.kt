package com.example.taskmaster.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.taskmaster.ui.common.buttons.CloseButton
import com.example.taskmaster.ui.common.factory.AnimationFactory.AnimatedColor
import com.example.taskmaster.ui.theme.gradientEnd
import com.example.taskmaster.ui.theme.gradientStart

@Composable
fun AddFloatingActionButton(
    buttonText: String,
    onClick: () -> Unit,
    isFBVisible: Boolean = true,
    content: @Composable () -> Unit
) {
    Scaffold(
        floatingActionButton =
        {
            AnimatedVisibility(visible = isFBVisible) {
                ExtendedFABButton(
                    buttonText = buttonText,
                    btnImage = Icons.Default.Add,
                    fabGradientColors = listOf(
                        AnimatedColor(gradientEnd, gradientStart).value,
                        AnimatedColor().value
                    )
                ) {
                    onClick()
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            CloseButton()
            content()
        }
    }
}
