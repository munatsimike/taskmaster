package com.example.taskmaster.ui.screen.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.taskmaster.ui.viewModel.SharedUserViewModel
import com.example.taskmaster.ui.viewModel.SharedViewModel
import com.example.taskmaster.ui.common.snackbar.CustomSnackbarHostState

import com.example.taskmaster.R
import com.example.taskmaster.data.remote.api.NetworkResponse
import com.example.taskmaster.domain.model.project.Project
import com.example.taskmaster.navigation.AppScreen
import com.example.taskmaster.ui.common.ConfirmDialog
import com.example.taskmaster.ui.common.imageDisplay.NetworkImageLoader
import com.example.taskmaster.ui.common.snackbar.DisplaySnackBar
import com.example.taskmaster.ui.model.UiMessage
import com.example.taskmaster.ui.viewModel.auth.AuthViewModel
import com.example.taskmaster.ui.viewModel.projects.ProjectFormViewModel
import com.example.taskmaster.ui.viewModel.projects.ProjectsViewModel
import com.example.taskmaster.ui.common.CustomCard
import com.example.taskmaster.ui.common.menu.DeleteEditOptionsMenu
import com.example.taskmaster.ui.common.DisplayProgressBar
import com.example.taskmaster.ui.common.FailureWithRetry
import com.example.taskmaster.ui.common.state.ProcessNetworkState
import com.example.taskmaster.ui.screen.BaseScreenWithFAB
import com.example.taskmaster.ui.screen.projects.forms.ProjectForm
import com.example.taskmaster.util.headerData

object Projects {
    @Composable
    fun MainScreen(
        navController: NavController,
        sharedUserViewModel: SharedUserViewModel,
        sharedViewModel: SharedViewModel,
        authViewModel: AuthViewModel,
        snackBarHostState: CustomSnackbarHostState,
        projectsViewModel: ProjectsViewModel = hiltViewModel(),
        projectFormViewModel: ProjectFormViewModel = hiltViewModel()
    ) {
        // Collect state from the ViewModel using collectAsState
        val loginUiState by authViewModel.uiState.collectAsState()
        val projects by projectsViewModel.projects.collectAsState()
        val loggedInUser by sharedUserViewModel.loggedInUser.collectAsState()
        val formUiState by projectFormViewModel.uiFormState.collectAsState()
        val screenState by projectsViewModel.screenState.collectAsState()

        val message = projectFormViewModel.getServerResponseMsg(formUiState, screenState)

        projectsViewModel.handleActions(
            screenState,
            formUiState
        ) { projectFormViewModel.toggleIsFormSubmitted() }

        LaunchedEffect(loggedInUser.id, screenState.triggerFetch) {
            projectsViewModel.getAllProjects()
        }

        DisplaySnackBar(
            uiMessage = message,
            customSnackbarHostState = snackBarHostState
        ) {
            projectsViewModel.clearUiScreenStateMessage(uiScreenState = screenState)
        }

        val onProjectClick = { project: Project ->
            sharedViewModel.setProject(project)
            navController.navigate(AppScreen.Dashboard.route)
        }

        // logout if the remote server fails to validate credentials
        val onInvalidCredentials = { authViewModel.logout() }

        DisplaySnackBar(
            uiMessage = UiMessage(),
            customSnackbarHostState = snackBarHostState
        ) { projectFormViewModel.clearServerResponseMessage() }

        BaseScreenWithFAB(
            isFabVisible = screenState.isFABVisible,
            fabBtnText = stringResource(id = R.string.add_project),
            onFabClick = {
                projectFormViewModel.showForm()
            },
            headerData = headerData(
                loggedInUser = loggedInUser,
                projectName = stringResource(id = R.string.empty_string),
                currentPage = AppScreen.Projects.title,
            ), onLogoutClick = {authViewModel.logout()}
        ) {
            // check if there is a bearer token
            if (loginUiState.hasToken) {
                HomeScreen(
                    onFABVisibility = { isVisible: Boolean ->
                        projectsViewModel.setFBVisibility(isVisible)
                    },
                    networkState = projects,
                    onInvalidCredentials = onInvalidCredentials,
                    onProjectClick = onProjectClick,
                    onEditProject = { project: Project ->
                        projectFormViewModel.handleEdit(project)
                    },
                    onDeleteProject = { item: String, projectId: String ->
                        projectsViewModel.handleDeleteItem(item, projectId)
                    },
                    onRetry = { projectFormViewModel.triggerDataFetch() }
                )
            } else {
                DisplayProgressBar()
            }
        }

        if (formUiState.isVisible) {
            ProjectForm(formUiState, projectFormViewModel)
        }

        if (screenState.deleteDialogState.isVisible) {
            ConfirmDialog(
                itemToDelete = screenState.deleteDialogState.selectedItem ?: "Project",
                onConfirm = {
                    projectsViewModel.deleteProject(screenState.deleteDialogState.selectedItemId)
                }) {
                projectsViewModel.hideConfirmDeleteDialog()
            }
        }
    }
}

@Composable
private fun HomeScreen(
    onFABVisibility: (Boolean) -> Unit,
    networkState: NetworkResponse<List<Project>>,
    onInvalidCredentials: () -> Unit,
    onProjectClick: (Project) -> Unit,
    onDeleteProject: (String, String) -> Unit,
    onEditProject: (Project) -> Unit,
    onRetry: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart
    ) {
        ProcessNetworkState(
            onInvalidCredentials = onInvalidCredentials,
            onErrorFailure = { msg: String ->
                FailureWithRetry(onRetry = onRetry, errorMsg = msg)
            },
            progressBarText = stringResource(id = R.string.loading_projects),
            fabVisibility = onFABVisibility,
            state = networkState
        ) { response: List<Project> ->
            DisplayProjects(
                response = response,
                onProjectClick = onProjectClick,
                onDeleteProject = onDeleteProject,
                onEditProject = onEditProject
            )
        }
    }
}

@Composable
private fun DisplayProjects(
    response: List<Project>,
    onProjectClick: (Project) -> Unit,
    onEditProject: (Project) -> Unit,
    onDeleteProject: (String, String) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(10.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(response) { index, project ->
            DisplayProject(
                cellIndex = index,
                project = project,
                onProjectClick = onProjectClick,
                onEditProject = onEditProject,
                onDeleteProject = onDeleteProject,
            )
        }
    }
}

@Composable
private fun DisplayProject(
    cellIndex: Int,
    project: Project,
    onProjectClick: (Project) -> Unit,
    onEditProject: (Project) -> Unit,
    onDeleteProject: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    CustomCard(
        modifier = modifier.aspectRatio(1f),
        cardBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart
        ) {
            project.thumbnailUrl?.let {
                NetworkImageLoader(
                    imageUrl = it, modifier = Modifier.fillMaxSize()
                )
            } ?: run {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray.copy(alpha = 0.3f))
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .align(Alignment.BottomCenter)
                .clickable { onProjectClick(project) }
            )
            ProjectCardHeader(
                cellIndex = cellIndex,
                project = project,
                onEditClick = onEditProject,
                onDeleteClick = onDeleteProject

            )
        }
    }
}

@Composable
private fun ProjectCardHeader(
    cellIndex: Int,
    project: Project,
    onDeleteClick: (String, String) -> Unit,
    onEditClick: (Project) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
                        Color.Transparent
                    ),
                    startY = 0f,
                    endY = 180f
                )
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .background(
                    Color.Black.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(topStart = 8.dp)
                )
                .padding(horizontal = 0.dp, vertical = 5.dp) // Balanced padding
        ) {
            // Location Icon and Project Name
            Icon(
                imageVector = Icons.Outlined.LocationOn,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Yellow
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = project.name,
                fontSize = 15.sp,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.weight(1f))

            // Delete/Edit Menu
            DeleteEditOptionsMenu(
                item = project,
                iconColor = Color.White,
                onDeleteClick = { onDeleteClick(project.name, project.id) },
                onEditClick = { onEditClick(project) },
                canDelete = true,
                offset = if (cellIndex % 2 == 0) DpOffset(
                    x = (-112).dp,
                    0.dp
                ) else DpOffset(x = 0.dp, y = 0.dp)
            )
        }
    }
}
