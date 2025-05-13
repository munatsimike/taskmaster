package com.teqie.taskmaster.ui.components.form

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(
    btnText: String = "Save Changes",
    buttonColor: Color = MaterialTheme.colorScheme.tertiary,
    isVisible: Boolean = true,
    onSaveBtnClick: () -> Unit
) {
    AnimatedVisibility(visible = isVisible) {
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            onClick = {
                onSaveBtnClick()
            },
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth(0.8f)
                .height(45.dp)
        ) {
            Text(text = btnText)
        }
    }
}