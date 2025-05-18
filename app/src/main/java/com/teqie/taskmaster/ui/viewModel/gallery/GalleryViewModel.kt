package com.teqie.taskmaster.ui.viewModel.gallery

import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.gallery.Folder
import com.teqie.taskmaster.domain.gallery.GalleryImage
import com.teqie.taskmaster.domain.useCases.gallery.DeleteFolderUseCase
import com.teqie.taskmaster.domain.useCases.gallery.GetFoldersUseCase
import com.teqie.taskmaster.domain.useCases.gallery.GetImagesUseCase
import com.teqie.taskmaster.ui.model.MessageType
import com.teqie.taskmaster.ui.viewModel.BaseViewModel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getFoldersUseCase: GetFoldersUseCase,
    private val getGalleryImagesUseCase: GetImagesUseCase,
    private val deleteFolderUseCase: DeleteFolderUseCase
) :
    BaseViewModel() {

    private val _folders = MutableStateFlow<Resource<List<Folder>>>(Resource.Loading)
    val folders: StateFlow<Resource<List<Folder>>> = _folders

    private val _selectedImage: MutableStateFlow<GalleryImage?> = MutableStateFlow(null)
    val selectedImage: StateFlow<GalleryImage?> = _selectedImage

    private val _images =
        MutableStateFlow<Resource<List<GalleryImage>>>(Resource.Loading)
    val images: StateFlow<Resource<List<GalleryImage>>> = _images

    fun getGalleryFolders(projectId: String) {
        viewModelScope.launch {
            getFoldersUseCase(projectId).collect {
                _folders.value = it
            }
        }
    }

    fun getGalleryImages(folderId: String) {
        viewModelScope.launch {
            delay(1000L)
            getGalleryImagesUseCase(folderId).collect {
                _images.value = it
            }
        }
    }

    fun onDeleteFolder(folderId: String?) {
        if (folderId != null) {
            viewModelScope.launch {
                deleteFolderUseCase(folderId).collect { apiResponse ->
                    processApiMessage(apiResponse) { messageType: MessageType, message: String ->
                        updateUiMessage(messageType, message)
                    }
                    hideConfirmDeleteDialog()
                }
            }
        }
    }


    fun onDeleteImage(image: GalleryImage) {
        selectedItem(image.imageName)
        selectedItemId(image.id)
        showConfirmDeleteDialog()
    }

    fun updateSelectedImage(image: GalleryImage, index: Int) {
        _selectedImage.value = image
        _screenState.update { it.copy(selectedIndex = index) }
    }

    fun clearImage() {
        _selectedImage.value = null
    }

    fun handleDescriptionClick(description: String) {
        setBottomSheetDescription(description)
        showBottomSheetModal()
    }
}