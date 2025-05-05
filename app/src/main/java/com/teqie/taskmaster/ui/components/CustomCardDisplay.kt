package com.teqie.taskmaster.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 10.dp,
    shape: Shape = RoundedCornerShape(12.dp), // Customizable shape
    backgroundColor: Color = MaterialTheme.colorScheme.surface, // Customizable background color
    contentColor: Color = contentColorFor(backgroundColor),
    onImageClick: (() -> Unit)? = null, // Optional click action
    cardBorderColor: Color = Color.Unspecified,
    cardContent: @Composable BoxScope.() -> Unit // Content of the card
) {
    Card(
        modifier = modifier.clickable(
            enabled = onImageClick != null,
            onClick = { onImageClick?.invoke() }),
        shape = shape,
        border = BorderStroke(1.dp, cardBorderColor),
        elevation = CardDefaults.cardElevation(elevation),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        )
    ) {
        Box {
            cardContent()
        }
    }
}