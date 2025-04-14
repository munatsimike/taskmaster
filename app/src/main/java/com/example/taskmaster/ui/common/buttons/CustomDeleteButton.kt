package com.example.taskmaster.ui.common.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.taskmaster.R
import com.example.taskmaster.ui.common.TextButtonWithIcon

@Composable
fun CustomDeleteButton(
    modifier: Modifier = Modifier,
    secondIconIsVisible: Boolean = false,
    enabled: Boolean = !secondIconIsVisible,
    secondIcon: ImageVector = Icons.Outlined.Info,
    secondIconTint: Color = MaterialTheme.colorScheme.primary,
    onDeleteClick: () -> Unit = {}
) {
    TextButtonWithIcon(
        modifier = modifier,
        btnText = stringResource(id = R.string.delete),
        icon = Icons.Outlined.Delete,
        onClick = onDeleteClick,
        iconTint = MaterialTheme.colorScheme.error,
        enabled = enabled,
        secondIconTint = secondIconTint,
        secondIconIsVisible = secondIconIsVisible,
        secondIcon = secondIcon
    )
}
