package com.teqie.taskmaster.ui.screen.gallery.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teqie.taskmaster.ui.components.factory.ButtonFactory.PrimaryButton
import com.teqie.taskmaster.ui.components.factory.TextFieldFactory.FilledTextField
import com.teqie.taskmaster.ui.viewModel.gallery.GalleryFormViewModel

@Composable
fun FolderManagementForm(
    projectId: String,
    galleryFormViewModel: GalleryFormViewModel
) {
    val folderState by galleryFormViewModel.folerState.collectAsState()

    val onNameChange: (String) -> Unit = { galleryFormViewModel.onNameChange(it) }
    val onDescriptionChange: (String) -> Unit = { galleryFormViewModel.onDescriptionChange(it) }
    val onAddFolder = {
        with(galleryFormViewModel) {
            onIdChange(projectId)
            createFormState()
            clearForm()
            closeForm()
            triggerDataFetch()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Ensures scrolling for smaller screens
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add Folder",
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 22.sp),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        FilledTextField(
            value = folderState.name,
            label = "Folder Name",
            onValueChange = onNameChange
        )

        FilledTextField(
            value = folderState.description,
            label = "Folder Description",
            onValueChange = onDescriptionChange,
            modifier = Modifier
                .height(120.dp) // Adjust height for descriptions
                .padding(top = 8.dp)
        )

        PrimaryButton(
            buttonText = "Create Folder",
            onButtonClick = onAddFolder,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}

