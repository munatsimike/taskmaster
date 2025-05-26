package com.teqie.taskmaster.ui.screen.projects.forms

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.teqie.taskmaster.R
import com.teqie.taskmaster.domain.model.file.FileType
import com.teqie.taskmaster.domain.model.project.Project
import com.teqie.taskmaster.ui.components.DisplayProgressBar
import com.teqie.taskmaster.ui.components.ErrorContent
import com.teqie.taskmaster.ui.components.FormModal
import com.teqie.taskmaster.ui.components.factory.ButtonFactory.PrimaryButton
import com.teqie.taskmaster.ui.components.factory.TextFieldFactory.FilledTextField
import com.teqie.taskmaster.ui.components.form.ImageUploader
import com.teqie.taskmaster.ui.model.FileState
import com.teqie.taskmaster.ui.model.ScreenState
import com.teqie.taskmaster.ui.uiState.FormState
import com.teqie.taskmaster.ui.viewModel.file.FileFormManagementViewModel
import com.teqie.taskmaster.ui.viewModel.projects.ProjectFormViewModel


@Composable
fun ProjectForm(
    formState: FormState,
    projectFormViewModel: ProjectFormViewModel,
    uploadFormViewModel: FileFormManagementViewModel = hiltViewModel(),
) {
    val fileState by uploadFormViewModel.fileState.collectAsState()
    var errorText by remember { mutableStateOf("") }
    val formDataState by projectFormViewModel.projectState.collectAsState()

    val onFileUploaded: (String) -> Unit = { uploadedUrl ->
        projectFormViewModel.onImageUrlChange(uploadedUrl)
    }

    when (fileState) {
        is ScreenState.Error -> {
            errorText = (fileState as ScreenState.Error).message
        }

        is FileState.Uploaded -> {
            onFileUploaded((fileState as FileState.Uploaded).uploadedUrl)
        }
        else -> Unit
    }

    val onSelectedFile = { uri: Uri? ->
        val fileName = uri?.let { uploadFormViewModel.getFileName(it) }
        if (fileName != null) {
            uploadFormViewModel.processFile(
                uri,
                fileName,
                FileType.IMAGE.name
            )
        }
    }

    Box {
        FormModal(
            formContent = {
                ProjectFormContent(
                    fileState = fileState,
                    errorText = errorText,
                    isEditing = formState.isEditing,
                    formState = formDataState,
                    onEditProject = projectFormViewModel::editFormState,
                    onNameChange = projectFormViewModel::onNameChange,
                    onDescriptionChange = projectFormViewModel::onDescriptionChange,
                    onAddressChange = projectFormViewModel::onAddressChange,
                    onCreateProject = projectFormViewModel::createFormState,
                    onSelectedFile = onSelectedFile
                )
            }
        ) {
            if (formState.isEditing) {
                projectFormViewModel.stopEditing()
                projectFormViewModel.clearForm()
            }
            projectFormViewModel.closeForm()
        }
    }
}

@Composable
private fun ProjectFormContent(
    fileState: ScreenState,
    errorText: String,
    isEditing: Boolean,
    formState: Project,
    onEditProject: () -> Unit,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onCreateProject: () -> Unit,
    onSelectedFile: (Uri?) -> Unit
) {
    val editProjectTxt = stringResource(id = R.string.edit_project)
    val createProjectTxt = stringResource(id = R.string.create_project)
    val actionText = if (isEditing) editProjectTxt else createProjectTxt

    // Form Fields
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = actionText,
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp)
        )

        if (errorText.isNotBlank()) ErrorContent(message = errorText)

        FilledTextField(
            value = formState.name,
            label = stringResource(id = R.string.name),
            onValueChange = { onNameChange(it) },
        )

        FilledTextField(
            value = formState.description,
            label = stringResource(id = R.string.description),
            onValueChange = { onDescriptionChange(it) },
            modifier = Modifier.height(80.dp)
        )
        FilledTextField(
            value = formState.address.orEmpty(),
            label = stringResource(id = R.string.address),
            onValueChange = { onAddressChange(it) },
            modifier = Modifier
                .height(130.dp)
                .fillMaxWidth(0.85f)
                .align(Alignment.CenterHorizontally)
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        ImageUploader(
            imageUrl = formState.thumbnailUrl,
            errorText = errorText,
            onSelectedFile = onSelectedFile,
        )

        if (fileState is ScreenState.Loading) {
            DisplayProgressBar(
                message = stringResource(id = R.string.loading_image),
                isAnimating = true
            )
        } else {
            PrimaryButton(buttonText = actionText, onButtonClick =  {
                if (isEditing) {
                    onEditProject()
                } else {
                    onCreateProject()
                }
            })
        }
    }
}