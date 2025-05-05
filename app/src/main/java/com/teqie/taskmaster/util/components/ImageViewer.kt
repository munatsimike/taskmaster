package com.teqie.taskmaster.util.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.teqie.taskmaster.R
import com.teqie.taskmaster.ui.components.imageDisplay.NetworkImageLoader

@Composable
fun ImageViewer(imageUrl: String?, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        if (imageUrl != null) {
            NetworkImageLoader(
                contentScale = ContentScale.Crop,
                imageUrl = imageUrl
            )
        } else {
            Image(
                contentScale = ContentScale.Fit,
                painter = painterResource(id = R.drawable.placeholder_image),
                contentDescription = null
            )
        }
    }
}