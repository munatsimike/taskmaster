package com.teqie.taskmaster.ui.common.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.teqie.taskmaster.R
import com.teqie.taskmaster.ui.common.TextButtonWithIcon

@Composable
fun CustomDownloadButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onDownloadClick: () -> Unit
) {
    TextButtonWithIcon(
        modifier = modifier,
        btnText = stringResource(id = R.string.download),
        icon = ImageVector.vectorResource(R.drawable.download_24px),
        onClick = onDownloadClick,
        enabled = enabled,
    )
}