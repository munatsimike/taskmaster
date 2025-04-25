package com.teqie.taskmaster.ui.common.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.teqie.taskmaster.R
import com.teqie.taskmaster.ui.common.TextButtonWithIcon

@Composable
fun CustomEditButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onEditClick: () -> Unit = {}
) {
    TextButtonWithIcon(
        modifier = modifier,
        btnText = stringResource(id = R.string.edit),
        icon = Icons.Outlined.Edit,
        onClick = onEditClick,
        enabled = enabled,
    )
}