package com.example.taskmaster.ui.common.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.taskmaster.R
import com.example.taskmaster.ui.common.buttons.CustomDeleteButton
import com.example.taskmaster.ui.common.buttons.CustomEditButton
import com.example.taskmaster.ui.model.MenuOption


@Composable
fun <T> DeleteEditOptionsMenu(
    item: T,
    onEditClick: (T) -> Unit,
    onDeleteClick: (T) -> Unit,
    canDelete: Boolean,
    offset: DpOffset = DpOffset(x = 0.dp, y = 0.dp),
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.MoreVert,
    iconColor: Color = Color.Unspecified,
    iconContentDescription: String = stringResource(id = R.string.more_options),
    containerColor: Color = Color.White,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp),
    tonalElevation: Dp = 4.dp
) {
    var isExpanded by remember { mutableStateOf(false) }
    val toggleIsExpanded = { isExpanded = !isExpanded }
    val menuItems = moreOptionsMenuItems(
        item = item,
        onDeleteClick = onDeleteClick,
        onEditClick = onEditClick,
        canDelete = canDelete
    )

    Box(modifier = modifier) {
        Icon(
            imageVector = icon,
            tint = iconColor,
            contentDescription = iconContentDescription,
            modifier = Modifier.clickable { toggleIsExpanded() }
        )
        Surface(
            shape = shape,
            tonalElevation = tonalElevation
        ) {
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                containerColor = containerColor,
                offset = offset,
                shape = shape
            ) {
                menuItems.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            item.onClick()
                            toggleIsExpanded()
                        },
                        text = {
                            item.content(Modifier.fillMaxWidth())
                        },

                        )
                    if (item != menuItems.last()) {
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

private fun <T> moreOptionsMenuItems(
    item: T,
    onEditClick: (T) -> Unit,
    onDeleteClick: (T) -> Unit,
    canDelete: Boolean
): List<MenuOption> {
    return listOf(
        MenuOption(
            content = { modifier -> CustomEditButton(modifier = modifier, onEditClick = { onEditClick(item) }) },
            onClick = { onEditClick(item) }
        ),
        MenuOption(
            content = { modifier ->
                CustomDeleteButton(
                    modifier = modifier,
                    secondIconIsVisible = !canDelete,
                    onDeleteClick = {onDeleteClick(item)}
                )
            },
            onClick = { onDeleteClick(item) }
        )
    )
}
