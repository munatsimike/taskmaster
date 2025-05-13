package com.teqie.taskmaster.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.teqie.taskmaster.R
import com.teqie.taskmaster.domain.util.FileExtension
import com.teqie.taskmaster.domain.util.getFileExtension
import com.teqie.taskmaster.ui.components.factory.TextFactory.CaptionText

@Composable
fun DocumentPreviewer(
    fileUri: String,
    size: Int = 50
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .border(
                    width = 2.dp,
                    shape = RoundedCornerShape(size = 7.dp),
                    color = if (fileUri.isBlank()) MaterialTheme.colorScheme.primary.copy(0.4f) else Color.Unspecified
                ),
        ) {
            val painter = when (fileUri.getFileExtension()) {
                FileExtension.PDF.extension -> painterResource(id = R.drawable.pdf_svgrepo_com)
                FileExtension.DOC.extension, FileExtension.DOCX.extension -> painterResource(id = R.drawable.word_1_)
                else -> painterResource(id = R.drawable.placeholder_image)
            }

            Image(
                painter = painter,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(size = 10.dp)),
                contentScale = ContentScale.FillBounds, // Ensures it fills the Box
                contentDescription = null
            )
        }
        CaptionText(
            text = "Preview",
        )
    }
}
