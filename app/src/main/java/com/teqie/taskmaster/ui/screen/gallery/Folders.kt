package com.teqie.taskmaster.ui.screen.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.teqie.taskmaster.R
import com.teqie.taskmaster.domain.gallery.Folder
import com.teqie.taskmaster.navigation.AppScreen
import com.teqie.taskmaster.ui.components.BottomSheetModal
import com.teqie.taskmaster.ui.components.ConfirmDialog
import com.teqie.taskmaster.ui.components.CustomCard
import com.teqie.taskmaster.ui.components.FormModal
import com.teqie.taskmaster.ui.components.factory.ButtonFactory.SecondaryButton
import com.teqie.taskmaster.ui.components.menu.DeleteEditOptionsMenu
import com.teqie.taskmaster.ui.components.snackbar.CustomSnackbarHostState
import com.teqie.taskmaster.ui.components.snackbar.DisplaySnackBar
import com.teqie.taskmaster.ui.components.state.ProcessNetworkState
import com.teqie.taskmaster.ui.screen.BaseScreenWithFAB
import com.teqie.taskmaster.ui.screen.gallery.forms.FolderManagementForm
import com.teqie.taskmaster.ui.viewModel.SharedUserViewModel
import com.teqie.taskmaster.ui.viewModel.SharedViewModel
import com.teqie.taskmaster.ui.viewModel.gallery.GalleryFormViewModel
import com.teqie.taskmaster.ui.viewModel.gallery.GalleryViewModel
import com.teqie.taskmaster.util.headerData

object Folders {
    @Composable
    fun FoldersMainScreen(
        sharedViewModel: SharedViewModel,
        navController: NavController,
        sharedUserViewModel: SharedUserViewModel,
        snackBarHostState: CustomSnackbarHostState,
        galleryFormViewModel: GalleryFormViewModel,
        galleryViewModel: GalleryViewModel = hiltViewModel()
    ) {
        val project by sharedViewModel.project.collectAsState()
        val loggedInUser by sharedUserViewModel.loggedInUser.collectAsState()
        val state by galleryViewModel.folders.collectAsState()
        val formUiState by galleryFormViewModel.uiFormState.collectAsState()
        val screenState by galleryViewModel.screenState.collectAsState()

        val message = galleryViewModel.getServerResponseMsg(formUiState, screenState)

        galleryViewModel.handleActions(
            screenState,
            formUiState
        ) { galleryFormViewModel.toggleIsFormSubmitted() }

        DisplaySnackBar(
            uiMessage = message,
            customSnackbarHostState = snackBarHostState
        ) {
            galleryFormViewModel.clearServerResponseMessage()
        }

        LaunchedEffect(project.id, screenState.triggerFetch) {
            galleryViewModel.syncFoldersToLocalDb(project.id)
            galleryViewModel.getGalleryFolders(project.id)
        }

        BaseScreenWithFAB(
            headerData = headerData(
                loggedInUser = loggedInUser,
                projectName = project.name,
                currentPage = AppScreen.Folders.title
            ),
            fabBtnText = "Add folder",
            isFabVisible = screenState.isFABVisible && !screenState.bottomSheetState.isVisible,
            onLogoutClick = { navController.popBackStack() },
            onFabClick = {galleryFormViewModel.showForm()}

        ) {
            BottomSheetModal(showModel = screenState.bottomSheetState.isVisible,
                sheetContent = { BottomSheetContent(screenState.bottomSheetState.content) },
                onDismissRequest = { galleryViewModel.hideBottomSheetModal() }) {
                ProcessNetworkState(
                    state = state,
                    progressBarText = stringResource(id = R.string.loading_folders),
                    fabVisibility = { isVisible: Boolean ->
                        galleryViewModel.setFBVisibility(isVisible)
                    }
                ) { folders: List<Folder> ->
                    DisplayFolders(
                        onDeleteFolder = { folderName: String, id: String ->
                            galleryViewModel.handleDeleteItem(folderName, id)
                        },
                        folders = folders,
                        onFolderClick = { _folderId: String ->
                            navController.navigate(
                                AppScreen.Gallery.createRoute(folderId = _folderId)
                            )
                        },
                        onDescriptionClick = { description: String ->
                            galleryViewModel.handleDescriptionClick(description)
                        },
                        onEditFolder = { folder: Folder ->
                            galleryFormViewModel.handleEditFolderRequest(folder)
                        })
                }
            }

            if (formUiState.isVisible) {
                FormModal(formContent = {
                    FolderManagementForm(
                        projectId = project.id,
                        galleryFormViewModel = galleryFormViewModel
                    )
                }) {
                    galleryFormViewModel.closeForm()
                }
            }

            if (screenState.deleteDialogState.isVisible) {
                ConfirmDialog(
                    itemToDelete = screenState.deleteDialogState.selectedItem ?: "",
                    onConfirm = {
                        galleryViewModel.onDeleteFolder(screenState.deleteDialogState.selectedItemId)

                    }) {
                    galleryViewModel.hideConfirmDeleteDialog()
                }
            }
        }
    }

    @Composable
    private fun DisplayFolders(
        onDeleteFolder: (String, String) -> Unit,
        onEditFolder: (Folder) -> Unit,
        folders: List<Folder>,
        onFolderClick: (String) -> Unit,
        onDescriptionClick: (String) -> Unit
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp), // Adaptive column size
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(folders) { folder ->
                FolderItem(
                    folder = folder,
                    onDeleteFolder = onDeleteFolder,
                    onDescriptionClick = onDescriptionClick,
                    onFolderClick = onFolderClick,
                    onEditFolder = onEditFolder,
                )
            }
        }
    }

    @Composable
    private fun FolderItem(
        folder: Folder,
        onDeleteFolder: (String, String) -> Unit,
        onEditFolder: (Folder) -> Unit,
        modifier: Modifier = Modifier,
        onDescriptionClick: (String) -> Unit,
        onFolderClick: (String) -> Unit,

        ) {
        CustomCard(
            modifier = modifier.fillMaxWidth()
        ) {
            Box {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize()
                        .clickable { onFolderClick(folder.id) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = folder.name.replaceFirstChar { it.uppercaseChar() },
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Image(
                        modifier = Modifier.size(55.dp), // Reduced size for better fit
                        painter = painterResource(id = R.drawable.folder),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                    )

                    SecondaryButton(
                        text = stringResource(R.string.description),
                        onClick = { folder.description?.let { onDescriptionClick(it) } },
                        icon = R.drawable.visibility_24px,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Box(modifier = Modifier.fillMaxWidth()) {
                    DeleteEditOptionsMenu(
                        item = folder,
                        onDeleteClick = { onDeleteFolder(folder.name, folder.id) },
                        onEditClick = { onEditFolder(folder) },
                        canDelete = true,
                        modifier = Modifier.padding(end = 5.dp, top = 8.dp).align(Alignment.TopEnd)
                    )
                }
            }
        }
    }

    @Composable
    fun BottomSheetContent(description: String?) {
        Surface(
            tonalElevation = 7.dp, color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)
                    .padding(12.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.folder_description),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (description != null) {
                    Text(
                        text = description,
                        fontSize = 15.sp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        text = "This folder has no description", fontSize = 15.sp
                    )
                }
            }
        }
    }
}


