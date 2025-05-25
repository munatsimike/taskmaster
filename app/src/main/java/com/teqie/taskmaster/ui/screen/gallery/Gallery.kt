package com.teqie.taskmaster.ui.screen.gallery

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.teqie.taskmaster.R
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.gallery.GalleryImage
import com.teqie.taskmaster.domain.model.file.FileType
import com.teqie.taskmaster.domain.util.FileExtension
import com.teqie.taskmaster.navigation.AppScreen
import com.teqie.taskmaster.ui.components.CustomCard
import com.teqie.taskmaster.ui.components.FormModal
import com.teqie.taskmaster.ui.components.factory.TextFactory.EmptyStateMessage
import com.teqie.taskmaster.ui.components.form.FileManagementForm
import com.teqie.taskmaster.ui.components.imageDisplay.NetworkImageLoader
import com.teqie.taskmaster.ui.components.snackbar.CustomSnackbarHostState
import com.teqie.taskmaster.ui.components.snackbar.DisplaySnackBar
import com.teqie.taskmaster.ui.components.state.ProcessNetworkState
import com.teqie.taskmaster.ui.model.ScreenType
import com.teqie.taskmaster.ui.screen.BaseScreenWithFAB
import com.teqie.taskmaster.ui.uiState.FormState
import com.teqie.taskmaster.ui.viewModel.SharedUserViewModel
import com.teqie.taskmaster.ui.viewModel.SharedViewModel
import com.teqie.taskmaster.ui.viewModel.auth.AuthViewModel
import com.teqie.taskmaster.ui.viewModel.file.FileFormManagementViewModel
import com.teqie.taskmaster.ui.viewModel.file.FileManagementViewModel
import com.teqie.taskmaster.ui.viewModel.gallery.GalleryViewModel
import com.teqie.taskmaster.util.headerData


object Gallery {

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun GalleryMainScreen(
        folderId: String,
        navController: NavHostController,
        sharedViewModel: SharedViewModel,
        sharedUserViewModel: SharedUserViewModel,
        authViewModel: AuthViewModel,
        sharedTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope,
        galleryViewModel: GalleryViewModel,
        snackBarHostState: CustomSnackbarHostState,
        fileViewModel: FileFormManagementViewModel,
        fileManagementViewModel: FileManagementViewModel = hiltViewModel(),

        ) {
        val project by sharedViewModel.project.collectAsState()
        val loggedInUser by sharedUserViewModel.loggedInUser.collectAsState()
        val state by galleryViewModel.images.collectAsState()
        val formUiState by fileViewModel.uiFormState.collectAsState()
        val screenUiState by galleryViewModel.screenState.collectAsState()

        // trigger data fetch if an action is successful
        galleryViewModel.handleActions(
            screenUiState,
            formUiState
        ) { fileViewModel.toggleIsFormSubmitted() }

        DisplaySnackBar(
            uiMessage = screenUiState.message,
            customSnackbarHostState = snackBarHostState
        ) {
            galleryViewModel.clearUiScreenStateMessage(uiScreenState = screenUiState)
        }

        LaunchedEffect(folderId, screenUiState.triggerFetch) {
            galleryViewModel.syncImageToLocalDb(folderId)
            galleryViewModel.getGalleryImages(folderId)
        }

        BaseScreenWithFAB(
            isFabVisible = screenUiState.isFABVisible,
            fabBtnText = "Add Image",
            onFabClick = {
                with(fileViewModel) {
                    onFileTypeChange(FileType.IMAGE)
                    onProjectIdChange(projectId = project.id)
                    onIdChange(folderId)
                    showForm()
                }
            },
            headerData = headerData(
                loggedInUser = loggedInUser,
                projectName = project.name,
                currentPage = AppScreen.Gallery.title,
                showBackBtn = true,
            ),
            onLogoutClick = { authViewModel.logout() },
            onBackButtonClick = { navController.popBackStack() }
        ) {
            GalleryScreenContent(
                selectedImageIndex = screenUiState.selectedIndex,
                networkState = state,
                fabVisibility = { isVisible: Boolean ->
                    galleryViewModel.setFBVisibility(isVisible)
                },
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
                onImageClick = { index: Int, image: GalleryImage ->
                    galleryViewModel.updateSelectedImage(image, index)
                    navController.navigate(AppScreen.ImageDetails.route)
                },
            ) { fileUrl: String, fileName: String, fileType: FileExtension, progress: (Int) -> Unit ->
                fileManagementViewModel.downloadFile(fileUrl, fileName, fileType, progress)
            }
        }

        DisplayFileManagementForm(
            formUiState,
            fileViewModel,
            { galleryViewModel.triggerDataFetch() })

    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    private fun GalleryScreenContent(
        selectedImageIndex: Int,
        fabVisibility: (Boolean) -> Unit,
        onImageClick: (Int, GalleryImage) -> Unit,
        networkState: Resource<List<GalleryImage>>,
        sharedTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope,
        onDownloadImage: (String, String, FileExtension, (Int) -> Unit) -> Unit
    ) {
        ProcessNetworkState(
            state = networkState,
            fabVisibility = fabVisibility,
            progressBarText = stringResource(id = R.string.loading_images)
        ) { images: List<GalleryImage> ->
            DisplayImages(
                images = images,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
                onDownloadImage = onDownloadImage,
                onImageClick = onImageClick,
                selectedImageIndex = selectedImageIndex
            )
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    private fun DisplayImages(
        selectedImageIndex: Int,
        images: List<GalleryImage>,
        onImageClick: (Int, GalleryImage) -> Unit,
        sharedTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope,
        onDownloadImage: (String, String, FileExtension, (Int) -> Unit) -> Unit
    ) {
        val listState = rememberLazyGridState()

        LaunchedEffect(selectedImageIndex) {
            listState.scrollToItem(selectedImageIndex)
        }

        if (images.isEmpty()) {
            EmptyStateMessage(ScreenType.GALLERY)
        } else {
            // Display a grid of images
            LazyVerticalGrid(
                state = listState,
                modifier = Modifier.padding(10.dp),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(images) { index, image ->
                    GridItem(
                        index = index,
                        image = image,
                        onImageClick = onImageClick,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    private fun GridItem(
        index: Int,
        image: GalleryImage,
        onImageClick: (Int, GalleryImage) -> Unit,
        sharedTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope,
        modifier: Modifier = Modifier
    ) {
        with(sharedTransitionScope) {
            CustomCard(
                modifier = modifier.sharedBounds(
                    rememberSharedContentState(key = image.id),
                    animatedVisibilityScope = animatedVisibilityScope
                )
                    .aspectRatio(1f),
                onImageClick = {
                    // Trigger the onClick callback
                    onImageClick(index, image)
                }
            ) {
                NetworkImageLoader(
                    imageUrl = image.imageUrl,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                )
            }
        }
    }

    @Composable
    private fun DisplayFileManagementForm(
        formUiState: FormState,
        fileViewModel: FileFormManagementViewModel,
        onRefresh: () -> Unit
    ) {
        if (formUiState.isVisible) {
            FormModal(formContent = {
                FileManagementForm(
                    isEditing = formUiState.isEditing,
                    fileViewModel = fileViewModel
                )
            }) {
                fileViewModel.clearForm()
                fileViewModel.closeForm()

                if (formUiState.isEditing)
                    fileViewModel.stopEditing()
            }
        }
    }
}