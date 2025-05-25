package com.teqie.taskmaster.ui.screen.orfi.form

import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teqie.taskmaster.R
import com.teqie.taskmaster.domain.model.teamMember.TeamMember
import com.teqie.taskmaster.domain.util.getUserIdByUsername
import com.teqie.taskmaster.ui.components.CustomCheckBox
import com.teqie.taskmaster.ui.components.FormModal
import com.teqie.taskmaster.ui.components.factory.ButtonFactory.PrimaryButton
import com.teqie.taskmaster.ui.components.factory.TextFieldFactory.FilledTextField
import com.teqie.taskmaster.ui.components.form.CustomDatePicker
import com.teqie.taskmaster.ui.components.form.SelectUserMenu
import com.teqie.taskmaster.ui.model.orfi.Orfi
import com.teqie.taskmaster.ui.uiState.FormState
import com.teqie.taskmaster.ui.viewModel.orfi.ORFIFormViewModel
import com.teqie.taskmaster.util.components.DisplayContentBox
import com.teqie.taskmaster.util.isoToReadableDate
import com.teqie.taskmaster.util.toIsoString
import java.time.LocalDate

@Composable
fun ORFIManagementForm(
    projectId: String,
    projectUsers: List<TeamMember>,
    orfiFormViewModel: ORFIFormViewModel,
) {

    val formUiState by orfiFormViewModel.uiFormState.collectAsState()
    val formDataState by orfiFormViewModel.orfiFormState.collectAsState()

    FormModal(formContent = {
        AddORFIFormContent(
            formDataState = formDataState,
            projectUsers = projectUsers,
            formUiState = formUiState,
            onIdChange = { orfiFormViewModel.onIdChange(projectId) },
            onDateChange = orfiFormViewModel::onDateChange,
            onResolvedChange = orfiFormViewModel::onResolvedChange,
            onCreateFormState = { orfiFormViewModel.createFormState() },
            onDueDateChange = orfiFormViewModel::onDueDateChange,
            onQuestionChange = orfiFormViewModel::onQuestionChange,
            onSelectedUser = { name: String, userName ->
                val userId = projectUsers.getUserIdByUsername(userName).orEmpty()
                orfiFormViewModel.onSelectedUser(name, userId)
            },
            onExpandDropDownMenu = { orfiFormViewModel.expandDropDownMenu() },
            onDismissDropDwnMenu = { orfiFormViewModel.collapseDropDownMenu() },
            onEditFormState = { orfiFormViewModel.editFormState() },
        )
    }) {
        orfiFormViewModel.handleFormDismiss()
    }
}

@Composable
private fun AddORFIFormContent(
    formDataState: Orfi,
    formUiState: FormState,
    onIdChange: () -> Unit,
    projectUsers: List<TeamMember>,
    onDateChange: (String) -> Unit,
    onEditFormState: () -> Unit,
    onResolvedChange: (Boolean) -> Unit,
    onCreateFormState: () -> Unit,
    onDueDateChange: (String) -> Unit,
    onQuestionChange: (String) -> Unit,
    onSelectedUser: (String, String) -> Unit,
    onExpandDropDownMenu: () -> Unit,
    onDismissDropDwnMenu: () -> Unit,
) {
    val createORFI = stringResource(R.string.create_orfi)
    Text(
        text = if (formUiState.isEditing) stringResource(R.string.edit_orfi) else createORFI,
        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp)
    )

    DisplayContentBox(
        label = if (formUiState.isEditing) stringResource(R.string.last_update) else stringResource(
            R.string.today_date
        ),
        content = formDataState.updatedAt.isoToReadableDate().ifBlank {
            onDateChange(LocalDate.now().toIsoString())
            formDataState.updatedAt.isoToReadableDate()
        }
    )

    formDataState.assignedUserName?.let {
        SelectUserMenu(
        selectedUser = it,
        isExpanded = formUiState.isDropDownExpanded,
        error = formUiState.formValidationErrors["user"],
        projectUsers = projectUsers,
        onSelectedUser = onSelectedUser,
        onDismissDropDwnMenu = onDismissDropDwnMenu,
        onExpandDropDownMenu = onExpandDropDownMenu,
    )
    }

    FilledTextField(
        value = formDataState.question,
        label = stringResource(R.string.question_theme),
        onValueChange =
        { newValue -> onQuestionChange(newValue) }, modifier = Modifier.height(115.dp)
    )

    CustomDatePicker(
        formDataState.dueDate,
        labelTxt = stringResource(R.string.due_date),
    ) { date ->
        onDueDateChange(date)
    }
    CustomCheckBox(isChecked = formDataState.resolved, onCheckedChange = {
        onResolvedChange(formDataState.resolved)
    }, content = {
        Text(text = stringResource(R.string.resolved))
    })

    PrimaryButton(
        buttonText = if (formUiState.isEditing) stringResource(R.string.save_changes) else createORFI,
        onButtonClick = {
            onIdChange()
            formDataState.assignedUserName?.let { projectUsers.getUserIdByUsername(it) }
            if (formUiState.isEditing) {
                onEditFormState()
            } else {
                onCreateFormState()
            }
        })
}
