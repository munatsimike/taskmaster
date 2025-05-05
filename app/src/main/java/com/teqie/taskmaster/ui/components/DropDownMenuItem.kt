package com.teqie.taskmaster.ui.components


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CustomDropDownMenuItem(
    text: String,
    icon: ImageVector? = null,
    iconColor: Color = Color.Unspecified,
    onClick: () -> Unit
) {
    DropdownMenuItem(text = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(imageVector = icon, tint =  iconColor, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = text)
        }
    }, onClick = { onClick() })
}