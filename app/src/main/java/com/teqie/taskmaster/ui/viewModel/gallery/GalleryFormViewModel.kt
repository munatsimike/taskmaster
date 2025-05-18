package com.teqie.taskmaster.ui.viewModel.gallery

import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.domain.gallery.Folder
import com.teqie.taskmaster.domain.gallery.FolderState
import com.teqie.taskmaster.domain.useCases.gallery.AddFolderUseCase
import com.teqie.taskmaster.ui.viewModel.BaseFormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryFormViewModel @Inject constructor(
    private val  addFolderUseCase: AddFolderUseCase
): BaseFormViewModel() {

    private val _folderFormState = MutableStateFlow(FolderState())
    val folerState: StateFlow<FolderState> = _folderFormState


    fun onNameChange(folderName: String){
        _folderFormState.update { it.copy(name = folderName) }
    }

    fun onDescriptionChange(description: String){
        _folderFormState.update { it.copy(description = description) }
    }

    override fun createFormState() {
       viewModelScope.launch {
           addFolderUseCase(_folderFormState.value)
       }
    }

    override fun editFormState() {
    }

    fun handleEditFolderRequest(folder: Folder){
        onFolderChange(folder)
        showEditForm()
    }

    fun onFolderChange(folder: Folder){

    }

    // on project id change
    override fun onIdChange(id: String) {
        _folderFormState.update { it.copy(projectId = id) }
    }

    override fun clearForm() {
       _folderFormState.value = FolderState()
    }

}
