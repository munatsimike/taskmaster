package com.teqie.taskmaster.ui.viewModel.file

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.mapper.Mapper.toCommonFile
import com.teqie.taskmaster.domain.model.file.FileData
import com.teqie.taskmaster.domain.model.file.FileType
import com.teqie.taskmaster.domain.model.file.MyFile
import com.teqie.taskmaster.domain.model.file.PresignedUrl
import com.teqie.taskmaster.domain.useCases.file.EditFileUseCase
import com.teqie.taskmaster.domain.useCases.file.GetPresignedUrlUseCase
import com.teqie.taskmaster.domain.useCases.file.SaveFileUseCase
import com.teqie.taskmaster.domain.useCases.file.UploadFileUseCase
import com.teqie.taskmaster.ui.model.FileState
import com.teqie.taskmaster.ui.model.MessageType
import com.teqie.taskmaster.ui.model.ScreenState
import com.teqie.taskmaster.ui.viewModel.BaseFormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class FileFormManagementViewModel @Inject constructor(
    private val uploadFileUseCase: UploadFileUseCase,
    private val getPresignedUrlUseCase: GetPresignedUrlUseCase,
    private val saveFileUseCase: SaveFileUseCase,
    private val editFileUseCase: EditFileUseCase,

    @ApplicationContext private val context: Context
) : BaseFormViewModel() {

    private val _formDataState = MutableStateFlow(FileData())
    val formDataState: StateFlow<FileData> = _formDataState

    private val _fileState = MutableStateFlow<ScreenState>(FileState.Idle)
    val fileState: StateFlow<ScreenState> = _fileState

    /**---------------- file upload operations ---------------------------**/

    fun processFile(fileUri: Uri?, fileName: String?, fileType: String) {
        _fileState.value = ScreenState.Loading
        viewModelScope.launch {
            if (fileName != null) {
                getPresignedUrlUseCase(fileName, fileType).collect {
                    when (it) {
                        is Resource.Error -> {
                            val message = it.exception.message.toString()
                            _fileState.value = ScreenState.Error(message)
                        }

                        is Resource.Success -> {
                            _fileState.value = FileState.PresignedUrlGenerated(it.data)
                            if (fileUri != null) {
                                uploadFileToPreSignedUrl(fileUri, preSignedUrl = it.data)
                            }
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    private fun uploadFileToPreSignedUrl(uri: Uri, preSignedUrl: PresignedUrl) {
        _fileState.value = ScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val file: File = createTempFileFromUri(fileUri = uri)
            val url = uploadFileUseCase(file, preSignedUrl)
            onFileUploaded(url)
        }
    }

    /** ------------------ save, delete, edit file operations ----------------**/

    fun onEditFileClick(file: MyFile) {
        _formDataState.value = file.toCommonFile()
    }

    override fun createFormState() {
        viewModelScope.launch {
            saveFileUseCase(_formDataState.value)
            handleSubmitBtnClick()
        }
    }

    override fun editFormState() {
        viewModelScope.launch {
            editFileUseCase(_formDataState.value).collect {
                processApiMessage(it) { messageType: MessageType, message: String ->
                    updateUiMessage(messageType, message)
                }
            }
            handleSubmitBtnClick()
        }
    }

    /**--------------------------------- form state management -------------------------------------**/

    private fun onFileUploaded(url: String) {
        if (url.isNotBlank()) {
            _fileState.value = FileState.Uploaded(url)
            _formDataState.update { it.copy(url = url) }
        }
    }

    fun onProjectIdChange(projectId: String) {
        _formDataState.update { it.copy(projectId = projectId) }
    }

    fun onTagsChange(tags: List<String>) {
        _formDataState.update { it.copy(tags = tags) }
    }

    fun onFileTypeChange(fileType: FileType) {
        _formDataState.update { it.copy(fileType = fileType) }
    }

    override fun onIdChange(id: String) {
        _formDataState.update { it.copy(id = id) }
    }

    fun
            onFileNameChange(fileName: String) {
        _formDataState.update { it.copy(fileName = fileName) }
    }

    fun onDescriptionChange(description: String) {
        _formDataState.update { it.copy(description = description) }
    }

    fun handleDismissForm(isEditing: Boolean) {
        if (isEditing) {
            stopEditing()
        }
        closeForm()
        clearForm()
    }

    fun handleOnAddFileClick(fileType: FileType, fileId: String){
        onFileTypeChange(fileType)
        onIdChange(fileId)
        showForm()
    }

    fun handleOnEditFileClick(file: MyFile){
        onEditFileClick(file)
        startEditing()
        showForm()
    }

    override fun clearForm() {
        _formDataState.value = FileData()
    }

    /**--------------------------------- helper functions -------------------------------------**/

    fun getFileName(uri: Uri): String? {
        var fileName: String? = null
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        return fileName
    }

    // Helper function to create a temporary file from the Uri
    private fun createTempFileFromUri(fileUri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(fileUri)
        val tempFile = File(context.cacheDir, "upload_file")
        inputStream?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return tempFile
    }
}