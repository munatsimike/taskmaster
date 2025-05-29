package com.teqie.taskmaster.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teqie.taskmaster.R
import com.teqie.taskmaster.ui.components.CustomCard
import com.teqie.taskmaster.ui.components.factory.ButtonFactory.SecondaryButton
import com.teqie.taskmaster.ui.components.factory.TextFactory.CaptionText
import com.teqie.taskmaster.ui.components.imageDisplay.DisplayImageVectorIcon
import com.teqie.taskmaster.ui.components.menu.DeleteEditOptionsMenu
import com.teqie.taskmaster.ui.model.IconWithText
import com.teqie.taskmaster.ui.theme.darkBlue
import com.teqie.taskmaster.util.Status

@Composable
fun <T> CustomScreenCard(
    item: T,
    onEditClick: (T) -> Unit,
    onDeleteClick: (T) -> Unit,
    hiddenContentItems: List<IconWithText>? = null,
    cardBorderColor: Color,
    tag: String,
    cardBodyContent: @Composable() (() -> Unit?)? = null,
    canDelete: Boolean = true,
    cardHeaderContent: @Composable () -> Unit,
) {
    CustomCard(cardBorderColor = cardBorderColor) {
        Box(modifier = Modifier.padding(3.dp)) {
            Column(
                modifier = Modifier.padding(5.dp)
                    .background(color = cardBorderColor.copy(0.07f))
                    .padding(5.dp)
            ) {
                cardHeaderContent()
                cardBodyContent?.let {
                    cardBodyContent()
                }

                hiddenContentItems?.let {
                    CardHiddenContent(hiddenContentItems)
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 5.dp, end = 10.dp)
            ) {
                DeleteEditOptionsMenu(
                    item = item,
                    onDeleteClick = onDeleteClick,
                    onEditClick = onEditClick,
                    canDelete = canDelete,
                    modifier = Modifier.padding(start = 5.dp, top = 8.dp)
                )
            }

            DisplayContentBox(
                shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp),
                padding = 6,
                content = tag,
                borderColor = Color.Unspecified,
                backgroundColor = cardBorderColor, // border color is the tag background color,
                contentColor = Color.White,
                modifier = Modifier
                    .align(Alignment.TopStart)
            )
        }
    }
}

@Composable
fun CardHorizontalBarGraph(
    progress: Float,
    progressBarColor: Int,
    progressBarText: String,
    progressExplanation: String,
) {
    Column {
        Row(
            modifier = Modifier.padding(top = 20.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            HorizontalBarGraph(
                progress = progress,
                progressBarColor = calculateStatus(progressBarColor).toColor(),
                progressBarText = progressBarText
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 10.dp, bottom = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CaptionText(
                text = progressExplanation,
            )
        }
    }
}

@Composable
fun CustomRowWithAssignedTeamMember(
    avaTaUrl: String?,
    assignedTeamMemberName: String,
    buttonText: String,
    borderColor: Color = darkBlue,
    btnIcon: Int = R.drawable.document_24px,
    onButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomAssignedTeamMember(
            avaTaUrl = avaTaUrl,
            name = assignedTeamMemberName
        )

        SecondaryButton(
            borderColor = borderColor,
            icon = btnIcon,
            text = buttonText,
            onClick = { onButtonClick() })
    }
}

@Composable
private fun CardHiddenContent(items: List<IconWithText>) {
    // State to track if the card is expanded or not
    var isVisible by remember { mutableStateOf(false) }
    val onClick = { isVisible = !isVisible }

    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }) {
        // hidden content, only visible when isVisible is true
        AnimateContentVisibility(isVisible = isVisible, onHide = { isVisible = false }) {
            Column(
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                IconWithTextRow(
                    items
                )
                Spacer(modifier = Modifier.height(25.dp))
            }
        }
        Row(
            modifier = Modifier.align(Alignment.BottomCenter), Arrangement.Center
        ) {
            KeyboardUpDownArrowIcon(
                isExpanded = isVisible,
            )
        }
    }
}

@Composable
private fun IconWithTextRow(items: List<IconWithText>) {
    items.forEachIndexed { _, item ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
        ) {
            DisplayImageVectorIcon(
                icon = ImageVector.vectorResource(id = item.icon),
                tint = item.iconColor
            )
            Text(
                text = item.text, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp)
            )
        }
    }
}

private fun Status.toColor(): Color {
    return when (this) {
        Status.NOT_STARTED -> Color.Gray
        Status.COMPLETED -> Color(0xFF35886A)
        Status.OVER_DUE -> Color(0xFFEE5850)
        Status.IN_PROGRESS -> Color(0xFFEBD513)
    }
}

private fun calculateStatus(completionPercentage: Int): Status {
    return when {
        completionPercentage == 0 -> Status.NOT_STARTED
        completionPercentage == 100 -> Status.COMPLETED
        completionPercentage > 100 -> Status.OVER_DUE
        else -> Status.IN_PROGRESS
    }
}