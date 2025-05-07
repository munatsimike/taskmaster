package com.teqie.taskmaster.ui.screen.teams.forms

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teqie.taskmaster.domain.model.teamMember.Role
import com.teqie.taskmaster.domain.model.user.CreateUserRequest
import com.teqie.taskmaster.ui.components.FormModal
import com.teqie.taskmaster.ui.components.factory.ButtonFactory.PrimaryButton
import com.teqie.taskmaster.ui.components.factory.TextFieldFactory.FilledTextField
import com.teqie.taskmaster.ui.components.form.ImageUploader
import com.teqie.taskmaster.ui.viewModel.auth.AuthViewModel
import com.teqie.taskmaster.ui.viewModel.teams.TeamsFormViewModel


@Composable
fun AddUserForm(
    projectId: String,
    teamsFormViewModel: TeamsFormViewModel,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val formDataState by teamsFormViewModel.createUseRequestState.collectAsState()
    val isPasswordVisible by authViewModel.isPasswordVisible.collectAsState()
    val formUiState by teamsFormViewModel.uiFormState.collectAsState()

    FormModal(
        formContent = {
            AddUserFormContent(
                formState = formDataState,
                isPasswordVisible = isPasswordVisible,
                projectId = projectId,
                onNameChange = teamsFormViewModel::onNameChange,
                onPasswordChange = teamsFormViewModel::onPasswordChange,
                onUsernameChange = teamsFormViewModel::onUsernameChange,
                onEmailChange = teamsFormViewModel::onEmailChange,
                onRoleSelected = teamsFormViewModel::onRoleChange,
                onExpandMenu = { teamsFormViewModel.expandDropDownMenu() },
                isExpanded = formUiState.isDropDownExpanded,
                onCollapseMenu = { teamsFormViewModel.collapseDropDownMenu() },
                onCreateUser = teamsFormViewModel::createAssignUser,
                onTogglePassVisibility = { authViewModel.togglePasswordVisibility() }
            )
        },
    ) { teamsFormViewModel.closeForm() }
}

@Composable
private fun AddUserFormContent(
    formState: CreateUserRequest,
    isPasswordVisible: Boolean,
    isExpanded: Boolean,
    projectId: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRoleSelected: (String) -> Unit,
    onExpandMenu: () -> Unit,
    onCollapseMenu: () -> Unit,
    onCreateUser: (String) -> Unit,
    onTogglePassVisibility: () -> Unit
) {

    Text(
        text = "Create User",
        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp)
    )

    FilledTextField(
        value = formState.name,
        label = "Name",
        onValueChange = { newValue -> onNameChange(newValue) })

    UserRoleSelector(
        userRole = formState.role,
        isExpanded = isExpanded,
        onRoleSelected = onRoleSelected,
        onExpandMenu = onExpandMenu,
        onCollapseMenu = onCollapseMenu
    )

    FilledTextField(
        value = formState.username,
        label = "Assigned budget",
        onValueChange =
        { newValue -> onUsernameChange(newValue) }, modifier = Modifier.fillMaxWidth(0.7f)
    )

    FilledTextField(
        value = formState.email,
        label = "Email",
        onValueChange = { newValue ->
            onEmailChange(newValue)
        })

    FilledTextField(
        value = formState.username,
        label = "Username",
        onValueChange = { newValue ->
            onUsernameChange(newValue)
        })

    FilledTextField(
        formState.password,
        "Password",
        onValueChange =
        { newValue -> onPasswordChange(newValue) },
        visualTransformation = if (isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None
    )

    ImageUploader(imageUrl = "", errorText = "") {

    }

    PrimaryButton(buttonText = "Create user", onButtonClick = {
        onCreateUser(projectId)
    })
}


@Composable
fun UserRoleSelector(
    userRole: String,
    isExpanded: Boolean,
    onRoleSelected: (String) -> Unit,
    onExpandMenu: () -> Unit,
    onCollapseMenu: () -> Unit
) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .animateContentSize()
            .height(45.dp)
            .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(10.dp))
            .clickable { onExpandMenu() }
    ) {

        Text(
            text = userRole.ifBlank { "Select Role" },
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(10.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onCollapseMenu() }
        ) {
            Role.entries.forEach { role ->
                DropdownMenuItem(
                    text = { Text(text = role.role) },
                    onClick = {
                        onRoleSelected(role.role)
                        onCollapseMenu()
                    }
                )
            }
        }
    }
}



