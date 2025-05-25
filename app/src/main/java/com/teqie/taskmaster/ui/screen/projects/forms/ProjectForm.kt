package com.teqie.taskmaster.ui.screen.projects.forms

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
import com.teqie.taskmaster.R
import com.teqie.taskmaster.domain.model.project.Project
import com.teqie.taskmaster.ui.components.ErrorContent
import com.teqie.taskmaster.ui.components.FormModal
import com.teqie.taskmaster.ui.components.factory.ButtonFactory.PrimaryButton
import com.teqie.taskmaster.ui.components.factory.TextFieldFactory.FilledTextField
import com.teqie.taskmaster.ui.uiState.FormState
import com.teqie.taskmaster.ui.viewModel.projects.ProjectFormViewModel

@Composable
fun ProjectForm(
    formState: FormState,
    projectFormViewModel: ProjectFormViewModel,
) {

    var errorText by remember { mutableStateOf("") }
    val formDataState by projectFormViewModel.projectState.collectAsState()

    Box {
        FormModal(
            formContent = {
                ProjectFormContent(
                    errorText = errorText,
                    formState = formState,
                    project = formDataState,
                    onEditProject = projectFormViewModel::editFormState,
                    onNameChange = projectFormViewModel::onNameChange,
                    onDescriptionChange = projectFormViewModel::onDescriptionChange,
                    onAddressChange = projectFormViewModel::onAddressChange,
                    onCreateProject = projectFormViewModel::createFormState

                )
            }
        ) {
            projectFormViewModel.closeForm()
        }
    }
}

@Composable
private fun ProjectFormContent(
    errorText: String,
    formState: FormState,
    project: Project,
    onEditProject: () -> Unit,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onCreateProject: () -> Unit,
) {
    val editProjectTxt = stringResource(id = R.string.edit_project)
    val createProjectTxt = stringResource(id = R.string.create_project)
    val actionText = if (formState.isEditing) editProjectTxt else createProjectTxt

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
            value = project.name,
            label = stringResource(id = R.string.name),
            onValueChange = { onNameChange(it) },
        )

        FilledTextField(
            value = project.description,
            label = stringResource(id = R.string.description),
            onValueChange = { onDescriptionChange(it) },
            modifier = Modifier.height(80.dp)
        )
        FilledTextField(
            value = project.address.orEmpty(),
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
        PrimaryButton(buttonText = actionText, onButtonClick = {
            if (formState.isEditing) {
                onEditProject()
            } else {
                onCreateProject()
            }
        })
    }
}