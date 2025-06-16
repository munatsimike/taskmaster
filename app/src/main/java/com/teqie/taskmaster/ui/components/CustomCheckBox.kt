package com.teqie.taskmaster.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CustomCheckBox(
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        //Checkbox(checked = isChecked, modifier = modifier, onCheckedChange = { onCheckedChange() })
        content()
    }
}