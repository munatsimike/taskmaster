package com.teqie.taskmaster.ui.components.form

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.teqie.taskmaster.R
import com.teqie.taskmaster.domain.model.teamMember.TeamMember
import com.teqie.taskmaster.ui.components.CustomDropDownMenuItem
import com.teqie.taskmaster.ui.components.ErrorContent

@Composable
fun SelectUserMenu(
    selectedUser: String,
    error: String? = null,
    isExpanded: Boolean,
    buttonLabelTxt: String = stringResource(id = R.string.assigned_to),
    projectUsers: List<TeamMember>,
    onSelectedUser: (String, String) -> Unit,
    onDismissDropDwnMenu: () -> Unit,
    onExpandDropDownMenu: () -> Unit
) {
    val btnContentColor =
        if (selectedUser.isNotBlank()) Color.Black else MaterialTheme.colorScheme.onPrimary
    Column (modifier = Modifier.fillMaxWidth(),  horizontalAlignment = Alignment.CenterHorizontally){
        buttonLabelTxt?.let {
            Text(text = it)
        }
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedButton(modifier = Modifier
            .animateContentSize() // Animates size changes
            .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = getBtnColor(userSelected = selectedUser.isNotBlank())),
            border = BorderStroke(1.dp, color = Color(28, 146, 255)),
            shape = RoundedCornerShape(10.dp),
            onClick = { onExpandDropDownMenu() }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = stringResource(id = R.string.trailing_icon),
                    tint = btnContentColor
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = selectedUser.ifBlank { stringResource(id = R.string.select_member) },
                    style = MaterialTheme.typography.titleMedium,
                    color = btnContentColor
                )
                DropdownMenu(expanded = isExpanded, onDismissRequest = {
                    onDismissDropDwnMenu()
                }) {
                    projectUsers.forEach { user ->
                        CustomDropDownMenuItem(icon = Icons.Outlined.Person, text = user.name) {
                            user.username?.let { onSelectedUser(user.name, it) }

                        }
                    }
                }
            }
        }

        error?.let {
            ErrorContent(message = error)
        }
    }
}

@Composable
private fun getBtnColor(userSelected: Boolean): Color {
    val btnColor: Color = if (userSelected) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.primary
    }

    val animatedColor by animateColorAsState(targetValue = btnColor, label = "")

    return animatedColor
}