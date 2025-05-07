package com.teqie.taskmaster.ui.screen.teams

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.teamMember.Role
import com.teqie.taskmaster.domain.model.teamMember.TeamMember
import com.teqie.taskmaster.ui.components.ConfirmDialog
import com.teqie.taskmaster.ui.components.factory.TextFactory.TitleText
import com.teqie.taskmaster.ui.components.imageDisplay.DisplayImageVectorIcon
import com.teqie.taskmaster.ui.components.snackbar.CustomSnackbarHostState
import com.teqie.taskmaster.ui.components.snackbar.DisplaySnackBar
import com.teqie.taskmaster.ui.components.state.ProcessNetworkState
import com.teqie.taskmaster.ui.screen.BaseScreenWithFAB
import com.teqie.taskmaster.ui.screen.teams.forms.AddUserForm
import com.teqie.taskmaster.ui.theme.lightOrange
import com.teqie.taskmaster.ui.theme.teamsColor
import com.teqie.taskmaster.ui.viewModel.SharedUserViewModel
import com.teqie.taskmaster.ui.viewModel.SharedViewModel
import com.teqie.taskmaster.ui.viewModel.auth.AuthViewModel
import com.teqie.taskmaster.ui.viewModel.teams.TeamsFormViewModel
import com.teqie.taskmaster.ui.viewModel.teams.TeamsViewModel
import com.teqie.taskmaster.util.components.CustomScreenCard
import com.teqie.taskmaster.util.components.DisplayContentBox
import com.teqie.taskmaster.util.components.ImageViewer
import com.teqie.taskmaster.util.headerData

object Teams {

    @Composable
    fun TeamsMainScreen(
        sharedViewModel: SharedViewModel,
        navController: NavController,
        authViewModel: AuthViewModel,
        snackBarHostState: CustomSnackbarHostState,
        sharedUserViewModel: SharedUserViewModel,
        teamsFormViewModel: TeamsFormViewModel = hiltViewModel(),
        teamsViewModel: TeamsViewModel = hiltViewModel()
    ) {
        val project by sharedViewModel.project.collectAsState()
        val loggedInUser by sharedUserViewModel.loggedInUser.collectAsState()
        val formUiState by teamsFormViewModel.uiFormState.collectAsState()
        val screenState by teamsViewModel.screenState.collectAsState()
        val state by teamsViewModel.teamsByProjectState.collectAsState()
        val message = teamsViewModel.getServerResponseMsg(formUiState, screenState)

        teamsViewModel.handleActions(
            screenState,
            formUiState
        ) { teamsFormViewModel.toggleIsFormSubmitted() }

        LaunchedEffect(project.id, screenState.triggerFetch) {
            teamsViewModel.getTeamsByProject(project.id)
        }

        DisplaySnackBar(
            uiMessage = message,
            customSnackbarHostState = snackBarHostState
        ) {
            teamsFormViewModel.clearServerResponseMessage()
        }
        BaseScreenWithFAB(
            fabBtnText = "Add user",
            isFabVisible = screenState.isFABVisible,
            onFabClick = { teamsFormViewModel.showForm() },
            headerData = headerData(
                loggedInUser = loggedInUser,
                projectName = project.name,
                currentPage = "Teams"
            ),
            onLogoutClick = { authViewModel.logout() },
            onBackButtonClick = { navController.popBackStack() }
        ) {

            MainScreenContent(
                state, onEdit = {

                },
                onDeleteClick = { user: TeamMember ->
                    teamsViewModel.handleDeleteItem(user.name, user.id)
                },
                fabVisibility = { isVisible: Boolean ->
                    teamsViewModel.setFBVisibility(isVisible = isVisible)
                }
            )

            if (formUiState.isVisible) {
                Box {
                    AddUserForm(
                        projectId = project.id,
                        teamsFormViewModel = teamsFormViewModel,
                    )
                }
            }
        }

        if (screenState.deleteDialogState.isVisible) {
            ConfirmDialog(
                itemToDelete = screenState.deleteDialogState.selectedItem ?: "",
                onConfirm = {
                    teamsViewModel
                },
                onDismiss = { teamsViewModel.hideConfirmDeleteDialog() },
            )
        }
    }
}

@Composable
private fun MainScreenContent(
    state: Resource<List<TeamMember>>,
    onEdit: (TeamMember) -> Unit,
    fabVisibility: (Boolean) -> Unit,
    onDeleteClick: (TeamMember) -> Unit
) {
    ProcessNetworkState(state = state, fabVisibility = fabVisibility) { teams: List<TeamMember> ->
        DisplayTeams(teams = teams, onEdit = onEdit, onDeleteClick = onDeleteClick)
    }
}

@Composable
private fun DisplayTeams(
    teams: List<TeamMember>,
    onEdit: (TeamMember) -> Unit,
    onDeleteClick: (TeamMember) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        if (teams.isNotEmpty()) {
            itemsIndexed(teams) { _, teamMember ->
                TeamMemberItem(teamMember, onDeleteClick = onDeleteClick, onEdit = onEdit)
            }
        } else {
            item {
                Text(text = "No teams members assigned to this project")
            }
        }
    }
}

@Composable
private fun TeamMemberItem(
    teamMember: TeamMember,
    onDeleteClick: (TeamMember) -> Unit,
    onEdit: (TeamMember) -> Unit
) {
    val roleCardBorderColor = if (teamMember.role == Role.MANAGER) teamsColor else lightOrange
    CustomScreenCard(
        tag = "Team member",
        item = teamMember,
        onDeleteClick = onDeleteClick,
        onEditClick = onEdit,
        cardHeaderContent = {
            Spacer(modifier = Modifier.height(17.dp))
            HeaderContent(teamMember, roleCardBorderColor.copy(0.5f))
        },
    )
}

@Composable
private fun HeaderContent(teamMember: TeamMember, roleBackGroundColor: Color) {
    val context = LocalContext.current
    Row(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageViewer(
                teamMember.avatarUrl,
                Modifier
                    .size(55.dp) // Size of the image
                    .border(
                        width = 2.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape
                    ) // Border around the image
                    .clip(CircleShape)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                DisplayContentBox(
                    backgroundColor = roleBackGroundColor,
                    padding = 5,
                    contentColor = Color.Black,
                    borderColor = Color.Transparent,
                    content = teamMember.role.role,
                )
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            TitleText(
                teamMember.name.replaceFirstChar { it.uppercaseChar() },
            )
            userDetailes(text = teamMember.email, Icons.Default.Email) {
                teamMember.email?.let {
                    SendEmail(
                        context,
                        it
                    )
                }
            }
            userDetailes(text = teamMember.phoneNumber, icon = Icons.Default.Phone) {
                teamMember.phoneNumber?.let { MakeACall(context, it.toInt()) }
            }
            userDetailes(text = teamMember.username, icon = Icons.Default.AccountBox) {}
        }
    }
}

@Composable
private fun userDetailes(text: String?, icon: ImageVector, onclick: () -> Unit) {
    if (!text.isNullOrBlank())
        Row(
            modifier = Modifier.clickable { onclick() },
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DisplayImageVectorIcon(icon = icon)
            Text(text)
        }
}

private fun MakeACall(context: Context, phoneNumber: Int) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    handleIntent(context, intent, "No dialer app found")
}

private fun SendEmail(context: Context, email: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email") // Only email apps should handle this
    }
    handleIntent(context, intent, "No email app found")
}

private fun handleIntent(context: Context, intent: Intent, errorMessage: String) {
    // Check if the intent can be resolved
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }
}

