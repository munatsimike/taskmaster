package com.teqie.taskmaster.util.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.teqie.taskmaster.R
import com.teqie.taskmaster.ui.components.factory.TextFactory.BodyText

@Composable
fun CustomAssignedTeamMember(avaTaUrl: String?, name: String, size: Int = 50) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageViewer(
            avaTaUrl,
            Modifier
                .size(
                    size.dp
                ) // Size of the image // Border around the image
                .clip(CircleShape)
        )
        // Spacer for optional alignment with other UI elements
        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.animateContentSize(),
            verticalArrangement = Arrangement.Center
        ) {
            BodyText(text = stringResource(R.string.assigned_to_short))
            // Assigned team member's name
            Text(
                text = name.substringBefore(" "), style = MaterialTheme.typography.titleMedium
            )
        }
    }
}