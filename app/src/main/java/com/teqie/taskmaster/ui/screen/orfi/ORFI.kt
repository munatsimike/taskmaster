package com.teqie.taskmaster.ui.screen.orfi

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.teqie.taskmaster.R
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.teamMember.TeamMember
import com.teqie.taskmaster.navigation.AppScreen
import com.teqie.taskmaster.navigation.navigateBasedOnToken
import com.teqie.taskmaster.ui.components.ConfirmDialog
import com.teqie.taskmaster.ui.components.factory.TextFactory.SubtitleText
import com.teqie.taskmaster.ui.components.factory.TextFactory.TitleText
import com.teqie.taskmaster.ui.components.imageDisplay.DisplayImageVectorIcon
import com.teqie.taskmaster.ui.components.snackbar.CustomSnackbarHostState
import com.teqie.taskmaster.ui.components.snackbar.DisplaySnackBar
import com.teqie.taskmaster.ui.components.state.ProcessNetworkState
import com.teqie.taskmaster.ui.model.IconWithText
import com.teqie.taskmaster.ui.model.orfi.Orfi
import com.teqie.taskmaster.ui.screen.BaseScreenWithFAB
import com.teqie.taskmaster.ui.screen.orfi.form.ORFIManagementForm
import com.teqie.taskmaster.ui.theme.orfiColor
import com.teqie.taskmaster.ui.viewModel.SharedUserViewModel
import com.teqie.taskmaster.ui.viewModel.SharedViewModel
import com.teqie.taskmaster.ui.viewModel.auth.AuthViewModel
import com.teqie.taskmaster.ui.viewModel.orfi.ORFIFormViewModel
import com.teqie.taskmaster.ui.viewModel.orfi.ORFIViewModel
import com.teqie.taskmaster.ui.viewModel.teams.TeamsViewModel
import com.teqie.taskmaster.util.components.CustomRowWithAssignedTeamMember
import com.teqie.taskmaster.util.components.CustomScreenCard
import com.teqie.taskmaster.util.components.DelayedAction
import com.teqie.taskmaster.util.headerData
import com.teqie.taskmaster.util.isoStringToLocalDate
import com.teqie.taskmaster.util.isoToReadableDate

object ORFI {

    @Composable
    fun ORFIMainScreen(
        navController: NavController,
        sharedViewModel: SharedViewModel,
        sharedUserViewModel: SharedUserViewModel,
        authViewModel: AuthViewModel,
        snackBarHostState: CustomSnackbarHostState,
        usersViewModel: TeamsViewModel = hiltViewModel(),
        orfiFormViewModel: ORFIFormViewModel = hiltViewModel(),
        orfiViewModel: ORFIViewModel = hiltViewModel()
    ) {
        val projectUsers = remember {
            mutableStateOf<List<TeamMember>>(emptyList())
        }

        val loginUiState by authViewModel.uiState.collectAsState()
        val project by sharedViewModel.project.collectAsState()
        val loggedInUser by sharedUserViewModel.loggedInUser.collectAsState()
        val state by orfiViewModel.orfiState.collectAsState()
        val formUiState by orfiFormViewModel.uiFormState.collectAsState()
        val usersState by usersViewModel.teamsByProjectState.collectAsState()
        val uiScreenState by orfiViewModel.screenState.collectAsState()
        val message = orfiFormViewModel.getServerResponseMsg(formUiState, uiScreenState)

        orfiViewModel.handleActions(
            uiScreenState,
            formUiState
        ) { orfiFormViewModel.toggleIsFormSubmitted() }

        DisplaySnackBar(
            uiMessage = message,
            customSnackbarHostState = snackBarHostState
        ) {
            orfiViewModel.clearUiScreenStateMessage(uiScreenState = uiScreenState)
        }

        ProcessNetworkState(state = usersState) { users ->
            projectUsers.value = users
        }

        LaunchedEffect(loginUiState.hasToken) {
            if (!loginUiState.hasToken) {
                navigateBasedOnToken(false, navController)
            }
        }

        LaunchedEffect(project.id, uiScreenState.triggerFetch) {
            orfiViewModel.syncOrfiToLocalDb(project.id)
            orfiViewModel.getORFI(project.id)
        }

        LaunchedEffect(project.id) {
            usersViewModel.getTeamsByProject(projectId = project.id)
        }

        BaseScreenWithFAB(
            isFabVisible = uiScreenState.isFABVisible,
            onFabClick = { orfiFormViewModel.showForm() },
            fabBtnText = "Add ORFI",
            headerData = headerData
                (
                loggedInUser = loggedInUser,
                projectName = project.name,
                currentPage = "ORFI"
            ),
            onBackButtonClick = { navController.popBackStack() },
            onLogoutClick = { authViewModel.logout() }
        ) {

            ORFIScreenContent(
                state, onEditClick = { orfi: Orfi ->
                    orfiFormViewModel.handleOrfiEditRequest(orfi)
                },
                onDeleteClick = { orfi: Orfi ->
                    orfiViewModel.showConfirmDeleteDialog(orfi)
                },
                onViewFileClick = { orfiId: String ->
                    navController.navigate(AppScreen.ORFIFile.createRoute(orfiId = orfiId))
                },
                fabVisibility = { isVisible: Boolean ->
                    orfiViewModel.setFBVisibility(isVisible)
                }
            )
        }

        if (formUiState.isVisible) {
            ORFIManagementForm(
                projectId = project.id,
                projectUsers = projectUsers.value,
                orfiFormViewModel = orfiFormViewModel
            )
        }

        if (uiScreenState.deleteDialogState.isVisible) {
            ConfirmDialog(
                itemToDelete = uiScreenState.deleteDialogState.selectedItem.orEmpty(),
                onConfirm = {
                    uiScreenState.deleteDialogState.selectedItemId?.let {
                        orfiViewModel.onDeleteOrfi(
                            it
                        )
                    }
                }) {
                orfiViewModel.hideConfirmDeleteDialog()
            }
        }
    }

    @Composable
    private fun ORFIScreenContent(
        state: Resource<List<Orfi>>,
        fabVisibility: (Boolean) -> Unit,
        onEditClick: (Orfi) -> Unit,
        onDeleteClick: (Orfi) -> Unit,
        onViewFileClick: (String) -> Unit
    ) {
        ProcessNetworkState(
            state = state,
            fabVisibility = fabVisibility,
            progressBarText = stringResource(id = R.string.loading_orfi),
        ) { orfis: List<Orfi> ->
            DisplayORFI(
                orfis = orfis,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
                onViewFileClick = onViewFileClick
            )
        }
    }

    @Composable
    private fun DisplayORFI(
        orfis: List<Orfi>,
        onEditClick: (Orfi) -> Unit,
        onDeleteClick: (Orfi) -> Unit,
        onViewFileClick: (String) -> Unit
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            itemsIndexed(orfis) { _, orfi ->
                ORFIItem(
                    orfi = orfi,
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick,
                    onViewFileClick = onViewFileClick
                )
            }
        }
    }

    @Composable
    private fun ORFIItem(
        orfi: Orfi,
        onEditClick: (Orfi) -> Unit,
        onDeleteClick: (Orfi) -> Unit,
        onViewFileClick: (String) -> Unit
    ) {

        CustomScreenCard(
            tag = "ORFI",
            item = orfi,
            onDeleteClick = onDeleteClick,
            onEditClick = onEditClick,
            cardBodyContent = {

                CardBodyContent(orfi.question)
            },
            hiddenContentItems = hiddenContentItems(orfi),
            cardHeaderContent = {
                OrfiItemHeaderContent(
                    orfi = orfi,
                    onViewFileClick = onViewFileClick
                )
            },
            cardBorderColor = orfiColor
        )
    }

    @Composable
    private fun OrfiItemHeaderContent(orfi: Orfi, onViewFileClick: (String) -> Unit) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            DisplayORFIStatus(orfi.resolved)
            if (!orfi.resolved) {
                SubtitleText(
                    text = "Remaining: ${orfi.getRemainingDays()} days",
                    textColor = getColor(orfi.getRemainingDays().toInt())
                )
            } else {
                SubtitleText(
                    textDecoration = TextDecoration.LineThrough,
                    text = "Due date: ${orfi.dueDate.isoStringToLocalDate()}",
                )
            }
        }

        orfi.assignedName?.let {
            CustomRowWithAssignedTeamMember(
                avaTaUrl = orfi.assignedAvatar,
                assignedTeamMemberName = it,
                buttonText = stringResource(id = R.string.view_file)
            ) {
                onViewFileClick(orfi.id)
            }
        }
    }

    @Composable
    private fun DisplayORFIStatus(isResolved: Boolean) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TitleText(
                text = "Task " + if (isResolved) "resolved" else "Unresolved",
            )
            Spacer(modifier = Modifier.width(8.dp))

            if (isResolved) {
                Box(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(17.dp)
                        .background(MaterialTheme.colorScheme.tertiary)
                ){
                    DisplayImageVectorIcon(icon = Icons.Filled.CheckCircle, iconSize = 17, tint = Color.White)
                }
            }
        }
    }

    @Composable
    fun CardBodyContent(
        text: String,
        maxLines: Int = 2 // Maximum number of lines to show when collapsed
    ) {
        var expanded by remember { mutableStateOf(false) }

        // Truncated text logic
        val shortText = remember(text) {
            if (text.length > 50) text.take(100) + "..." else text
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .animateContentSize(
                    spring(
                        dampingRatio = 0.7f, // Smooth and slightly elastic
                        stiffness = 150f // Faster settling
                    )
                )
        ) {
            Text(
                text = "Question",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.CenterHorizontally),
            )

            Crossfade(
                targetState = expanded,
                animationSpec = tween(1000),
                label = "crossFade"
            ) { visible ->
                when (visible) {
                    true -> {
                        DisplayQuestion(
                            txt = text,
                            isExpanded = expanded,
                            maxLines = maxLines,
                            modifier = Modifier
                        )
                    }

                    false -> {
                        DisplayQuestion(
                            txt = shortText,
                            isExpanded = expanded,
                            maxLines = maxLines,
                            modifier = Modifier
                        )
                    }
                }
            }

            if (text.length > 100) {

                if (expanded) {
                    DelayedAction(key = Unit, action = { expanded = !expanded })
                }

                Text(
                    text = if (expanded) "Show less" else "Show more",
                    color = Color.Blue,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable { expanded = !expanded }
                        .align(Alignment.End)
                )
            } else {
                Spacer(modifier = Modifier.padding(5.dp))
            }
        }
    }

    @Composable
    private fun hiddenContentItems(
        orfi: Orfi
    ): List<IconWithText> {
        return listOf(

            IconWithText(
                R.drawable.update_24px, "Last update: " + orfi.updatedAt.isoToReadableDate(),
            ),

            IconWithText(
                R.drawable.calendar_month_24px, "Due: ${orfi.dueDate.isoToReadableDate()}",
            ),
        )
    }
}

@Composable
private fun DisplayQuestion(
    txt: String,
    isExpanded: Boolean,
    maxLines: Int,
    modifier: Modifier
) {
    Text(
        text = txt,
        style = MaterialTheme.typography.bodyMedium,
        fontSize = 16.sp,
        maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
        lineHeight = 25.sp,
        modifier = modifier
    )
}

fun getColor(remainingDays: Int): Color {
    return when { // Overdue tasks
        remainingDays <= 9 -> Color.Red             // Less than or equal to 6 days remaining
        remainingDays in 10..20 -> Color(0xFFCC8400) // Orange for 7 to 15 days remaining
        else -> Color.Black                         // More than 15 days remaining
    }
}