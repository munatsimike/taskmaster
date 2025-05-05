package com.teqie.taskmaster.ui.viewModel.budgetPhase.file

import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.domain.model.file.FileType
import com.teqie.taskmaster.domain.useCases.file.DeleteFileUseCase
import com.teqie.taskmaster.domain.useCases.file.DownloadFileUseCase
import com.teqie.taskmaster.domain.util.FileExtension
import com.teqie.taskmaster.ui.model.MessageType
import com.teqie.taskmaster.ui.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FileManagementViewModel @Inject constructor(
    private val deleteFileUseCase: DeleteFileUseCase,
    private val downloadFileUseCase: DownloadFileUseCase
) : BaseViewModel() {

    fun onIsCheckedChange(checkedId: String) {
        _screenState.update {
            it.copy(selectedItemIds = if( it.selectedItemIds.contains(checkedId)){
                it.selectedItemIds.toMutableList().apply { remove(checkedId) }
            }else{
                it.selectedItemIds.toMutableList().apply { add(checkedId) }
            })
        }
    }

    fun onDeleteFile(fileId: String?, fileType: FileType) {
        viewModelScope.launch {
            if (fileId != null) {
                deleteFileUseCase(fileId, fileType).collect {
                    processApiMessage(it) { messageType: MessageType, message: String ->
                        updateUiMessage(messageType, message)
                    }
                }
            }
            hideConfirmDeleteDialog()
        }
    }

    fun downloadFile(fileUrl: String, fileName: String, fileType: FileExtension, progress:(Int)-> Unit){
        viewModelScope.launch {
            downloadFileUseCase(fileUrl, fileName, fileType, progress)
        }
    }
}