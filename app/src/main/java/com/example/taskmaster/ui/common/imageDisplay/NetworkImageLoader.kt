package com.example.taskmaster.ui.common.imageDisplay

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.example.taskmaster.R


/**
 * This displays images from the remote saver.
 **/

@Composable
fun NetworkImageLoader(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    imageDescription: String? = null,
) {
    Image(
        painter = rememberAsyncImagePainter(
            model = imageUrl,
            contentScale = contentScale,
            placeholder = painterResource(
                id = R.drawable.img
            ), error = painterResource(id = R.drawable.placeholder_image)
        ),
        contentDescription = imageDescription,
        contentScale = contentScale,
        modifier = modifier.fillMaxSize(),
    )
}
