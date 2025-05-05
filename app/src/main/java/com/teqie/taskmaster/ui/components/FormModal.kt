package com.teqie.taskmaster.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun FormModal(
    formContent: @Composable ColumnScope.() -> Unit,
    onDismiss: () -> Unit
) {
    // Full-screen dialog
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false) // Full-screen dialog
    ) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp
        val modalWidth = screenWidth * 0.9f // 90% of the screen

        Surface(
            modifier = Modifier
                .animateContentSize()
                .width(modalWidth.dp)
                .border(
                    width = 1.dp,
                    color = Color.Unspecified,
                    shape = RoundedCornerShape(12.dp)
                ),
            shape = RoundedCornerShape(12.dp), // Ensures the surface itself has rounded corners
            color = MaterialTheme.colorScheme.surface, // Optional: set the background color
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                Alignment.CenterHorizontally
            ) {
                formContent()
            }
        }
    }
}
