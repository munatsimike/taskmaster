package com.teqie.taskmaster.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.teqie.taskmaster.R
import com.teqie.taskmaster.ui.components.factory.ButtonFactory.PrimaryButton
import com.teqie.taskmaster.ui.components.imageDisplay.DisplayImageVectorIcon

@Composable
fun FailureWithRetry(errorMsg: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        DisplayImageVectorIcon(
            icon = ImageVector.vectorResource(id = R.drawable.wifi_off_24px),
            iconSize = 100
        )

        ErrorContent(errorMsg)
        PrimaryButton(
            buttonText = stringResource(id = R.string.retry),
            onButtonClick = { onRetry() }
        )
    }
}