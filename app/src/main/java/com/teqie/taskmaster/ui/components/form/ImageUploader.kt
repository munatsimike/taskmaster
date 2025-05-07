package com.teqie.taskmaster.ui.components.form

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teqie.taskmaster.R
import com.teqie.taskmaster.ui.components.CustomCard
import com.teqie.taskmaster.ui.components.imageDisplay.NetworkImageLoader


@Composable
fun ImageUploader(
    imageUrl: String?,
    errorText: String,
    onSelectedFile: (Uri?) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        ImagePreview(
            imageUrl = imageUrl,
            onSelectedFile = onSelectedFile,
            errorText = errorText
        )
        AnimatedVisibility(visible = (imageUrl != null)) {
            RemoveImageText(
                onClick = {
                    onSelectedFile(null) // Remove image by passing null
                }
            )
        }
    }
}

@Composable
private fun ImagePreview(
    imageUrl: String?,
    onSelectedFile: (Uri?) -> Unit,
    errorText: String
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        CustomCard(
            modifier = Modifier.size(120.dp),
            cardBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
        ) {
            if (!imageUrl.isNullOrBlank()) {
                NetworkImageLoader(imageUrl = imageUrl)
            } else {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.placeholder_image),
                    contentDescription = stringResource(id = R.string.placeholder_image_desc),
                    modifier = Modifier.size(150.dp)
                )
            }
        }

        FileSelector(
            actionText = if (imageUrl == null) stringResource(id = R.string.select_image) else "",
            errorText = errorText,
            selector = { selectorText, onClick ->
                SelectorBox(selectorText = selectorText, onClick = onClick)
            },
            onFileSelected = onSelectedFile
        )
    }
}

@Composable
private fun SelectorBox(
    selectorText: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.TopCenter
    ) {
        Text(text = selectorText, modifier = Modifier.padding(5.dp))
    }
}

@Composable
private fun RemoveImageText(
    onClick: () -> Unit
) {
    Text(
        text = stringResource(id = R.string.remove_image),
        fontSize = 14.sp,
        modifier = Modifier
            .clickable { onClick() }
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(7.dp),
                color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f)
            )
            .padding(9.dp)
    )
}
