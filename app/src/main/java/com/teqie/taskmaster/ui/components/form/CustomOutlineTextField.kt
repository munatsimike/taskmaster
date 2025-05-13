package com.teqie.taskmaster.ui.components.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.teqie.taskmaster.ui.components.ErrorContent


@Composable
fun CustomOutlineTextField(
    value: String,
    labelTxt: String,
    enabled: Boolean = true,
    error: String? = null,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable () -> Unit = {},
    fullWidth: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Column(modifier = if (fullWidth) modifier.fillMaxWidth() else modifier) {
        OutlinedTextField(
            enabled = enabled,
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text(labelTxt) },
            modifier = if (fullWidth) modifier.fillMaxWidth() else modifier.align(Alignment.CenterHorizontally),
            colors = customTxtFieldColors(),
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )

        error?.let {
            ErrorContent(message = it)
        }
    }
}
