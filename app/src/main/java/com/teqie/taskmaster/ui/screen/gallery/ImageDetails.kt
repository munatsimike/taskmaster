package com.teqie.taskmaster.ui.screen.gallery

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teqie.taskmaster.R
import com.teqie.taskmaster.domain.gallery.GalleryImage
import com.teqie.taskmaster.domain.model.file.FileType
import com.teqie.taskmaster.domain.model.project.Project
import com.teqie.taskmaster.ui.components.ConfirmDialog
import com.teqie.taskmaster.ui.components.CustomCard
import com.teqie.taskmaster.ui.components.buttons.CustomDeleteButton
import com.teqie.taskmaster.ui.components.buttons.CustomDownloadButton
import com.teqie.taskmaster.ui.components.buttons.CustomEditButton
import com.teqie.taskmaster.ui.components.imageDisplay.DisplayImageVectorIcon
import com.teqie.taskmaster.ui.components.imageDisplay.NetworkImageLoader
import com.teqie.taskmaster.ui.components.state.ProcessNetworkState
import com.teqie.taskmaster.ui.viewModel.SharedViewModel
import com.teqie.taskmaster.ui.viewModel.file.FileManagementViewModel
import com.teqie.taskmaster.ui.viewModel.gallery.GalleryViewModel
import com.teqie.taskmaster.util.components.DisplayContentBox


object ImageDetails {

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun MaingScreen(
        navController: NavController,
        animatedVisibilityScope: AnimatedVisibilityScope,
        sharedTransitionScop: SharedTransitionScope,
        galleryViewModel: GalleryViewModel,
        fileManagementViewModel: FileManagementViewModel,
        sharedViewModel: SharedViewModel
    ) {
        val selectedImage by galleryViewModel.selectedImage.collectAsState()
        val networkState by galleryViewModel.images.collectAsState()
        val project by sharedViewModel.project.collectAsState()
        val uiScreenState by galleryViewModel.screenState.collectAsState()

        ProcessNetworkState(
            networkState,
            progressBarText = stringResource(id = R.string.loading_images)
        ) { images: List<GalleryImage> ->

            ImageDetailsContent(
                images = images,
                project = project,
                selectedImage = selectedImage,
                onBack = { navController.popBackStack() },
                animatedVisibilityScope = animatedVisibilityScope,
                sharedTransitionScope = sharedTransitionScop,
                onImageClick = { index: Int, image: GalleryImage ->
                    galleryViewModel.updateSelectedImage(image, index)
                },
                onDelete = { image: GalleryImage ->
                    galleryViewModel.onDeleteImage(image)

                },
                onEdit = {},
                onDownload = {},
                onShare = {}
            )
        }
        if (uiScreenState.deleteDialogState.isVisible) {
            DisplayConfirmDeleteDialogBox(
                imageId = uiScreenState.deleteDialogState.selectedItemId,
                imageName = uiScreenState.deleteDialogState.selectedItem,
                onConfirmDelete = {
                    uiScreenState.deleteDialogState.selectedItemId?.let { it1 ->
                        fileManagementViewModel.onDeleteFile(
                            it1, FileType.IMAGE
                        )
                    }
                    galleryViewModel.hideConfirmDeleteDialog()
                    navController.popBackStack()
                },
                onDismiss = { galleryViewModel.hideConfirmDeleteDialog() }
            )
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    private fun ImageDetailsContent(
        project: Project,
        images: List<GalleryImage>,
        onImageClick: (Int, GalleryImage) -> Unit,
        selectedImage: GalleryImage?,
        modifier: Modifier = Modifier,
        onBack: () -> Unit,
        onDelete: (GalleryImage) -> Unit,
        onEdit: (GalleryImage) -> Unit,
        onDownload: () -> Unit,
        onShare: () -> Unit,
        sharedTransitionScope: SharedTransitionScope,
        animatedVisibilityScope: AnimatedVisibilityScope
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            selectedImage?.let { image ->
                DisplayImageTags(image, project)
                Spacer(modifier = Modifier.height(12.dp))
                with(sharedTransitionScope) {
                    NetworkImageLoader(
                        imageUrl = image.imageUrl,
                        modifier = modifier.sharedBounds(
                            rememberSharedContentState(key = image.id),
                            animatedVisibilityScope = animatedVisibilityScope
                        ).clickable { onBack() }.aspectRatio(1f)
                    )
                }

                Spacer(modifier = modifier.height(12.dp))
                ImageLazyRow(images, onImageClick)
                Spacer(modifier = modifier.height(12.dp))
                HorizontalDivider()
                ImageActionButtons(
                    image = image,
                    onDelete = onDelete,
                    onEdit = onEdit,
                    onDownload = onDownload,
                    onShare = onShare
                )

            } ?: run {
                Text(text = "no image selected")
            }
        }
    }

    @Composable
    private fun ImageActionButtons(
        image: GalleryImage,
        onDelete: (GalleryImage) -> Unit,
        onEdit: (GalleryImage) -> Unit,
        onDownload: () -> Unit,
        onShare: () -> Unit
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomDownloadButton { onDownload() }
            CustomEditButton { onEdit(image) }
            TextButton(
                enabled = true,
                onClick = { onShare() },
            ) {
                DisplayImageVectorIcon(icon = Icons.Default.Share)
                Text(text = "Share")
            }
            CustomDeleteButton { onDelete(image) }
        }
    }
}

@Composable
private fun DisplayImageTags(image: GalleryImage, project: Project) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(10.dp).fillMaxWidth(),
    ) {
        Text(
            text = image.imageName,
            style = MaterialTheme.typography.titleMedium
        )
        image.description?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Text(
            text = project.name,
            style = MaterialTheme.typography.bodyMedium
        )
        Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
            image.tags.forEach { tag ->
                DisplayContentBox(padding = 6, content = tag)
            }
        }
    }
}

@Composable
private fun ImageLazyRow(
    images: List<GalleryImage>,
    onImageClick: (Int, GalleryImage) -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val lazyRowHeight = screenHeight * 0.10f // 25% of the screen height

    LazyRow(
        modifier = Modifier.padding(10.dp)
            .height(lazyRowHeight), horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        itemsIndexed(images) { index, image ->
            LazyRowItem(image, index, onImageClick, lazyRowHeight)
        }
    }
}

@Composable
private fun LazyRowItem(
    image: GalleryImage,
    index: Int,
    onImageClick: (Int, GalleryImage) -> Unit,
    rowHeight: Dp
) {
    CustomCard(
        modifier = Modifier
            .size(rowHeight)
    ) {
        NetworkImageLoader(
            modifier = Modifier.clickable { onImageClick(index, image) },
            imageUrl = image.imageUrl
        )
    }
}

@Composable
private fun DisplayConfirmDeleteDialogBox(
    imageName: String?,
    imageId: String?,
    onConfirmDelete: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    imageId?.let {
        ConfirmDialog(itemToDelete = imageName ?: "this item", onConfirm = {
            onConfirmDelete(imageId)
        }) {
            onDismiss()
        }
    }
}
