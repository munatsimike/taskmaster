package com.teqie.taskmaster.ui.screen.bugdetPhase.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teqie.taskmaster.R
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.budget.BudgetPhaseFormData
import com.teqie.taskmaster.domain.model.teamMember.TeamMember
import com.teqie.taskmaster.domain.util.getUserIdByUsername
import com.teqie.taskmaster.ui.components.DisplayProgressBar
import com.teqie.taskmaster.ui.components.FormModal
import com.teqie.taskmaster.ui.components.factory.ButtonFactory.PrimaryButton
import com.teqie.taskmaster.ui.components.factory.TextFieldFactory.FilledTextField
import com.teqie.taskmaster.ui.components.form.CustomDatePicker
import com.teqie.taskmaster.ui.components.form.SelectUserMenu
import com.teqie.taskmaster.ui.components.state.ProcessNetworkState
import com.teqie.taskmaster.ui.uiState.FormState
import com.teqie.taskmaster.ui.viewModel.budgetPhase.BudgetFormViewModel
import com.teqie.taskmaster.ui.viewModel.teams.TeamsViewModel


@Composable
fun ManageBudgetPhaseForm(
    projectId: String,
    formState: FormState,
    usersViewModel: TeamsViewModel = hiltViewModel(),
    budgetFormViewModel: BudgetFormViewModel
) {
    val projectUsers = remember {
        mutableStateOf<List<TeamMember>>(emptyList())
    }

    LaunchedEffect(projectId) {
        usersViewModel.syncTeamsToLocalDb(projectId)
        usersViewModel.getAllTeamMembers()
    }

    val formDataState by budgetFormViewModel.newBudgetState.collectAsState()
    val usersState by usersViewModel.teamMembersState.collectAsState()

    ProcessNetworkState(state = usersState) { users ->
        projectUsers.value = users
    }

    FormModal(formContent = {
        AddBudgetPhaseFormContent(
            isLoadingUsers = usersState is Resource.Loading,
            formDataState = formDataState,
            formState = formState,
            onPhaseChange = budgetFormViewModel::onPhaseChange,
            onBudgetChange = budgetFormViewModel::onBudgetChange,
            onRevisedBudgetChange = budgetFormViewModel::onRevisedBudgetChange,
            onProgressChange = budgetFormViewModel::onProgressChange,
            onTotalDurationChange = budgetFormViewModel::onDurationChange,
            onStartDateChange = budgetFormViewModel::onStartDateChange,
            projectUsers = projectUsers.value,
            onUserSelector = { budgetFormViewModel.expandDropDownMenu() },
            onSelectedUser = { name: String, userName ->
                val userId = projectUsers.value.getUserIdByUsername(userName).orEmpty()
                budgetFormViewModel.onSelectedUser(name, userId)
            },
            onCreateBudget = {
                budgetFormViewModel.handleOnCreateBudgetPhase(projectId)

            },
            onUpdateBudgetPhase = { budgetFormViewModel.editFormState() },
            onDismissDropDwnMenu = {
                budgetFormViewModel.collapseDropDownMenu()
            }
        )
    }) {
        budgetFormViewModel.clearForm()
        budgetFormViewModel.closeForm()
    }
}

@Composable
private fun AddBudgetPhaseFormContent(
    isLoadingUsers: Boolean,
    formState: FormState,
    projectUsers: List<TeamMember>,
    formDataState: BudgetPhaseFormData,
    onPhaseChange: (String) -> Unit,
    onBudgetChange: (String) -> Unit,
    onRevisedBudgetChange: (String) -> Unit,
    onProgressChange: (String) -> Unit,
    onTotalDurationChange: (String) -> Unit,
    onStartDateChange: (String) -> Unit,
    onCreateBudget: () -> Unit,
    onUpdateBudgetPhase: () -> Unit,
    onSelectedUser: (String, String) -> Unit,
    onDismissDropDwnMenu: () -> Unit,
    onUserSelector: () -> Unit
) {
    val formTitle =
        if (formState.isEditing) stringResource(id = R.string.edit_budget) else stringResource(id = R.string.create_budget)

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = formTitle,
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp)
        )

        Text(
            text = stringResource(id = R.string.all_fields_mandatory),
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
        )
    }

    FilledTextField(
        formDataState.phase,
        stringResource(id = R.string.phase),
        errorMessage = formState.formValidationErrors["phase"], onValueChange =
        { onPhaseChange(it) })

    if (isLoadingUsers) {
        DisplayProgressBar(
            message = stringResource(id = R.string.loading_users),
            isAnimating = true
        )

    } else {
        SelectUserMenu(
            selectedUser = formDataState.assignedToName,
            isExpanded = formState.isDropDownExpanded,
            error = formState.formValidationErrors["user"],
            projectUsers = projectUsers,
            onSelectedUser = onSelectedUser,
            onDismissDropDwnMenu = onDismissDropDwnMenu,
            onExpandDropDownMenu = onUserSelector,
        )
    }

    FilledTextField(
        formDataState.initBudget,
        stringResource(id = R.string.init_budget),
        errorMessage = formState.formValidationErrors["budget"], onValueChange =
        { onBudgetChange(it) })

    if (formState.isEditing) {
        FilledTextField(
            formDataState.revisedBudget,
            "Revised budget",
            errorMessage = formState.formValidationErrors["budget"], onValueChange =
            { onRevisedBudgetChange(it) })
    }

    if (!formState.isEditing) {
        CustomDatePicker(formDataState.startDate) {
            onStartDateChange(it)
        }

        FilledTextField(
            formDataState.totalDuration,
            stringResource(id = R.string.total_duration_in_months),
            errorMessage = formState.formValidationErrors["totalDuration"], onValueChange =
            {
                onTotalDurationChange(it)
            })

        FilledTextField(
            formDataState.progress,
            stringResource(id = R.string.progress_in_months),
            errorMessage = formState.formValidationErrors["progress"], onValueChange =
            { onProgressChange(it) })
    }

    PrimaryButton(
        buttonText = if (formState.isEditing) stringResource(id = R.string.save_changes) else stringResource(
            id = R.string.create_budget
        ), onButtonClick = {
            if (formState.isEditing) {
                onUpdateBudgetPhase()
            } else {
                onCreateBudget()

            }
        })
}
