package com.teqie.taskmaster.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.teqie.taskmaster.ui.components.buttons.CloseButton
import com.teqie.taskmaster.ui.components.factory.AnimationFactory.AnimatedColor
import com.teqie.taskmaster.ui.theme.darkBlue

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
                        AnimatedColor(darkBlue, darkBlue).value,
                        darkBlue
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
