package com.teqie.taskmaster.ui.components.form

import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teqie.taskmaster.R
import com.teqie.taskmaster.domain.model.file.FileData
import com.teqie.taskmaster.domain.model.file.FileType
import com.teqie.taskmaster.ui.components.DisplayProgressBar
import com.teqie.taskmaster.ui.components.DocumentPreviewer
import com.teqie.taskmaster.ui.components.factory.ButtonFactory.PrimaryButton
import com.teqie.taskmaster.ui.model.FileState
import com.teqie.taskmaster.ui.model.ScreenState

import com.teqie.taskmaster.ui.viewModel.file.FileFormManagementViewModel

// form used to manage file related operations like adding or editing a file.
@Composable
fun FileManagementForm(
    isEditing: Boolean,
    fileViewModel: FileFormManagementViewModel,
) {
    // States
    var errorText by remember { mutableStateOf("") }

    val formDataState by fileViewModel.formDataState.collectAsState()
    val fileState by fileViewModel.fileState.collectAsState()

    val message = stringResource(id = R.string.no_file_selected)

    if (fileState is ScreenState.Error) {
        errorText = (fileState as ScreenState.Error).message
    }

    val onFileSelected: (Uri?) -> Unit = { uri ->
        val fileName = uri?.let {
            fileViewModel.getFileName(it)
        }
        if (fileName != null) {
            fileViewModel.onFileNameChange(fileName)
        }
        fileViewModel.processFile(uri, fileName, FileType.ORFIFile.name)
    }

    FormContent(
        fileState = fileState,
        formDataState = formDataState,
        isEditing = isEditing,
        errorText = errorText,
        onFileSelected = onFileSelected,
        onNameChange = fileViewModel::onFileNameChange,
        onDescriptionChange = fileViewModel::onDescriptionChange,

        ) {
        if (isEditing) {
            fileViewModel.editFormState()
            fileViewModel.startEditing()
        } else if (fileState is FileState.Uploaded) {
            fileViewModel.createFormState()
        } else {
            errorText = message
        }
        fileViewModel.closeForm()
    }
}

@Composable
private fun FormContent(
    fileState: ScreenState,
    formDataState: FileData,
    isEditing: Boolean,
    errorText: String,
    onFileSelected: (Uri?) -> Unit,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onBtnClick: () -> Unit
) {
    // UI Layout
    Column(
        modifier = Modifier
            .animateContentSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (isEditing) stringResource(id = R.string.edit_file) else stringResource(id = R.string.add_file),
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp)
            )
        }

        // File Selector (only when adding a new file)
        if (!isEditing) {
            FileSelector(errorText = errorText, onFileSelected = onFileSelected)
        }

        DocumentPreviewer(formDataState.fileName)

        // Form Fields
        CustomOutlineTextField(
            value = formDataState.fileName,
            labelTxt = stringResource(id = R.string.file_name),
            keyboardType = KeyboardType.Text,
            onValueChange = { onNameChange(it) }
        )

        CustomOutlineTextField(
            value = formDataState.description,
            labelTxt = stringResource(id = R.string.description),
            onValueChange = { onDescriptionChange(it) },
            modifier = Modifier.height(120.dp)
        )
        // Action Buttons or Progress
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (fileState is ScreenState.Loading) {
                DisplayProgressBar(
                    message = stringResource(id = R.string.uploading_file),
                    isAnimating = true,
                )
            } else {
                PrimaryButton(buttonText = if (isEditing) stringResource(id = R.string.save_changes) else stringResource(
                    id = R.string.add_file
                ),
                    onButtonClick = {
                        onBtnClick()
                    })
            }
        }
    }
}