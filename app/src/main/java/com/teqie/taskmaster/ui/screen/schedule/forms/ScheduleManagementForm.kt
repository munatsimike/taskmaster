package com.example.taskflow.ui.screen.schedule.forms

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskflow.R
import com.example.taskflow.domain.model.project.Project
import com.example.taskflow.ui.screen.schedule.ScheduleFormState
import com.example.taskflow.ui.util.components.ButtonFactory.PrimaryButton
import com.example.taskflow.ui.util.components.DisplayImageVectorIcon
import com.example.taskflow.ui.util.components.TextFieldFactory.FilledTextField
import com.example.taskflow.ui.util.components.form.CustomDatePicker
import com.example.taskflow.ui.util.components.form.FormModal
import com.example.taskflow.ui.util.formatFloat
import com.example.taskflow.ui.viewModel.schedule.ScheduleFormViewModel

@Composable
fun ManageScheduleForm(
    scheduleFormState: ScheduleFormState,
    scheduleFormViewModel: ScheduleFormViewModel,
    project: Project
) {
    val formDataState by scheduleFormViewModel.scheduleFormDataState.collectAsState()
    val onDismissForm = { scheduleFormViewModel.closeForm() }

    FormModal(formContent = {
        ScheduleManagementFormContent(
            schedule = scheduleFormState,
            project = project,
            onDismiss = onDismissForm,
            formDataState = formDataState,
            onStartDateChange = scheduleFormViewModel::onStartDateChange,
            onDurationChange = scheduleFormViewModel::onDurationChange,
            onProgressChange = scheduleFormViewModel::onProgressChange,
            onSaveChanges = { scheduleFormViewModel.onSaveChanges() }
        )
    }) { onDismissForm() }
}

//edit schedule form
@Composable
private fun ScheduleManagementFormContent(
    schedule: ScheduleFormState,
    formDataState: ScheduleFormState,
    onStartDateChange: (String) -> Unit,
    onDurationChange: (String) -> Unit,
    onProgressChange: (String) -> Unit,
    onSaveChanges: () -> Unit,
    project: Project,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier.animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Arrange elements with space between
    ) {
        ScheduleFormHeader(schedule, projectName = project.name) { onDismiss() }
        ScheduleFormFields(
            formDataState = formDataState,
            onStartDateChange = onStartDateChange,
            onDurationChange = onDurationChange,
            onProgressChange = onProgressChange,
        )
        PrimaryButton(buttonText = "Save Schedule", onButtonClick = {
            onSaveChanges()
        })
    }
}

@Composable
private fun ScheduleFormHeader(
    schedule: ScheduleFormState,
    modifier: Modifier = Modifier,
    projectName: String,
    onDismiss: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth(),
    ) {
        val iconTextColor = Color.Red
        Row(modifier = modifier.fillMaxWidth()) {
            Spacer(modifier = modifier.weight(1f))
            Row(
                modifier = modifier
                    .clickable { onDismiss() }
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close),
                    modifier = modifier.size(21.dp),
                    tint = iconTextColor
                )
                Text(text = stringResource(id = R.string.close), color = iconTextColor)
            }
        }

        Spacer(modifier = modifier.height(15.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            DisplayImageVectorIcon(
                icon = ImageVector.vectorResource(id = R.drawable.edit_24px),
                iconSize = 23
            )
            Text(
                text = stringResource(id = R.string.edit_schedule),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Text(
            text = projectName,
            fontSize = 16.sp,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = schedule.phase.replaceFirstChar { it.uppercaseChar() } + " " + stringResource(
                id = R.string.phase
            ),
            fontSize = 18.sp,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
private fun ScheduleFormFields(
    formDataState: ScheduleFormState,
    onStartDateChange: (String) -> Unit,
    onDurationChange: (String) -> Unit,
    onProgressChange: (String) -> Unit
) {
    // Form Fields
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomDatePicker(formDataState.startDate) { date ->
            onStartDateChange(date)
        }

        FilledTextField(
            value = formDataState.totalDuration,
            label = stringResource(id = R.string.total_duration_in_months), onValueChange =
            { newValue ->
                onDurationChange(newValue)
            })

        FilledTextField(
            value = if (formDataState.progress.isNotBlank()) formatFloat(formDataState.progress.toFloat()) else formDataState.progress,
            label = stringResource(id = R.string.progress), onValueChange =
            { newValue ->
                onProgressChange(newValue)
            })
    }
}