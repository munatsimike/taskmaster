package com.teqie.taskmaster.ui.screen.orfi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.teqie.taskmaster.domain.model.file.FileType
import com.teqie.taskmaster.domain.model.orfi.ORFIFile
import com.teqie.taskmaster.navigation.AppScreen
import com.teqie.taskmaster.navigation.navigateBasedOnToken
import com.teqie.taskmaster.ui.components.ConfirmDialog
import com.teqie.taskmaster.ui.components.DisplayFiles
import com.teqie.taskmaster.ui.components.FormModal
import com.teqie.taskmaster.ui.components.form.FileManagementForm
import com.teqie.taskmaster.ui.components.snackbar.CustomSnackbarHostState
import com.teqie.taskmaster.ui.components.snackbar.DisplaySnackBar
import com.teqie.taskmaster.ui.screen.BaseScreenWithFAB
import com.teqie.taskmaster.ui.viewModel.SharedUserViewModel
import com.teqie.taskmaster.ui.viewModel.SharedViewModel
import com.teqie.taskmaster.ui.viewModel.auth.AuthViewModel
import com.teqie.taskmaster.ui.viewModel.file.FileFormManagementViewModel
import com.teqie.taskmaster.ui.viewModel.file.FileManagementViewModel
import com.teqie.taskmaster.ui.viewModel.orfi.ORFIViewModel
import com.teqie.taskmaster.util.headerData

object ORFIFiles {

    @Composable
    fun ORFIFILEMainScreen(
        orfiId: String,
        navController: NavController,
        sharedUserViewModel: SharedUserViewModel,
        authViewModel: AuthViewModel,
        sharedViewModel: SharedViewModel,
        snackbarHostState: CustomSnackbarHostState,
        fileFormViewModel: FileFormManagementViewModel,
        fileViewModel: FileManagementViewModel,
        orfiViewModel: ORFIViewModel = hiltViewModel()
    ) {
        val loginUiState by authViewModel.uiState.collectAsState()
        val allOrfiFiles by orfiViewModel.orfiFiles.collectAsState()
        val project by sharedViewModel.project.collectAsState()
        val loggedInUser by sharedUserViewModel.loggedInUser.collectAsState()
        val formUiState by fileFormViewModel.uiFormState.collectAsState()
        val screenState by fileViewModel.screenState.collectAsState()
        val message = fileViewModel.getServerResponseMsg(formUiState, screenState)

        fileViewModel.handleActions(
            screenState,
            formUiState
        ) { fileFormViewModel.toggleIsFormSubmitted() }

        DisplaySnackBar(
            uiMessage = message,
            customSnackbarHostState = snackbarHostState
        ) {
            fileViewModel.clearUiScreenStateMessage(uiScreenState = screenState)
        }

        LaunchedEffect(loginUiState.hasToken) {
            if (!loginUiState.hasToken) {
                navigateBasedOnToken(false, navController)
            }
        }

        LaunchedEffect(orfiId, screenState.triggerFetch) {
            orfiViewModel.syncOrfiFileToLocalDb(orfiId)
            orfiViewModel.getORFIFiles(orfiId)
        }

        BaseScreenWithFAB(
            fabBtnText = "Add ORFI file",
            isFabVisible = true,
            onFabClick = { fileFormViewModel.handleOnAddFileClick(FileType.ORFIFile, orfiId) },
            headerData = headerData(
                loggedInUser = loggedInUser,
                projectName = project.name,
                currentPage = AppScreen.ORFIFile.title,
                showBackBtn = true
            ),
            onLogoutClick = { authViewModel.logout() },
            onBackButtonClick = { navController.popBackStack() }
        ) {

            DisplayFiles(
                checkedIds = screenState.selectedItemIds,
                networkState = allOrfiFiles,
                onCheckedChange = fileViewModel::onIsCheckedChange,
                onDeleteClick = { orfiFile: ORFIFile ->
                    fileViewModel.handleDeleteItem("ORFI File", orfiFile.id)
                },
                onEdit = { orfiFile: ORFIFile ->
                    fileFormViewModel.handleOnEditFileClick(orfiFile)
                }
            )
        }

        if (formUiState.isVisible) {
            FormModal(formContent = {
                FileManagementForm(
                    isEditing = formUiState.isEditing,
                    fileViewModel = fileFormViewModel
                )
            }) {
                fileFormViewModel.handleDismissForm(formUiState.isEditing)
            }
        }

        if (screenState.deleteDialogState.isVisible) {
            ConfirmDialog(itemToDelete = "Orfi file", onConfirm = {
                fileViewModel.onDeleteFile(
                    screenState.deleteDialogState.selectedItemId, FileType.ORFIFile
                )
            }) {
                fileViewModel.hideConfirmDeleteDialog()
            }
        }
    }
}