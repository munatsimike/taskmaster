package com.teqie.taskmaster.ui.screen.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.teqie.taskmaster.R
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.Schedule
import com.teqie.taskmaster.navigation.AppScreen
import com.teqie.taskmaster.ui.components.Tooltip
import com.teqie.taskmaster.ui.components.factory.TextFactory.SubtitleText
import com.teqie.taskmaster.ui.components.factory.TextFactory.TitleText
import com.teqie.taskmaster.ui.components.snackbar.CustomSnackbarHostState
import com.teqie.taskmaster.ui.components.snackbar.DisplaySnackBar
import com.teqie.taskmaster.ui.components.state.ProcessNetworkState
import com.teqie.taskmaster.ui.model.IconWithText
import com.teqie.taskmaster.ui.screen.DisplayScreenHeader
import com.teqie.taskmaster.ui.screen.schedule.forms.ManageScheduleForm
import com.teqie.taskmaster.ui.theme.lightGray
import com.teqie.taskmaster.ui.viewModel.SharedUserViewModel
import com.teqie.taskmaster.ui.viewModel.SharedViewModel
import com.teqie.taskmaster.ui.viewModel.schedule.ScheduleFormViewModel
import com.teqie.taskmaster.ui.viewModel.schedule.ScheduleViewModel
import com.teqie.taskmaster.util.components.CardHorizontalBarGraph
import com.teqie.taskmaster.util.components.CustomScreenCard
import com.teqie.taskmaster.util.formatFloat
import com.teqie.taskmaster.util.headerData
import com.teqie.taskmaster.util.isoToReadableDate

object Schedules {
    private var projectTile = ""

    @Composable
    fun SchedulesMainScreen(
        navController: NavHostController,
        sharedUserViewModel: SharedUserViewModel,
        sharedViewModel: SharedViewModel,
        snackBarHostState: CustomSnackbarHostState,
        scheduleFormViewModel: ScheduleFormViewModel = hiltViewModel(),
        scheduleViewModel: ScheduleViewModel = hiltViewModel()
    ) {
        val project by sharedViewModel.project.collectAsState()
        projectTile = project.name

        val selectedSchedule = remember {
            mutableStateOf<Schedule?>(null)
        }

        val loggedInUser by sharedUserViewModel.loggedInUser.collectAsState()
        val uiScreenState by scheduleViewModel.screenState.collectAsState()
        val formState by scheduleFormViewModel.uiFormState.collectAsState()
        val scheduleFormDataState by scheduleFormViewModel.scheduleFormDataState.collectAsState()

        scheduleViewModel.handleActions(
            uiScreenState,
            formState
        ) { scheduleFormViewModel.toggleIsFormSubmitted() }

        DisplaySnackBar(
            uiMessage = uiScreenState.message,
            customSnackbarHostState = snackBarHostState
        ) { scheduleFormViewModel.clearServerResponseMessage() }

        LaunchedEffect(project.id, uiScreenState.triggerFetch) {
            scheduleViewModel.syncScheduleToLocalDb(project.id)
            scheduleViewModel.getSchedule(project.id)
        }
        val state by scheduleViewModel.scheduleState.collectAsState()

        Column (modifier = Modifier.background(color = lightGray)) {
            DisplayScreenHeader(
                headerData = headerData(
                    loggedInUser = loggedInUser,
                    projectName = projectTile,
                    currentPage = AppScreen.Schedule.title
                )
            ) { navController.popBackStack() }
            ScheduleContent(
                onDismissTooltip = { selectedSchedule.value = null },
                selectedSchedule = selectedSchedule.value,
                networkState = state,
                onDeleteSchedule = { schedule: Schedule ->
                    selectedSchedule.value = schedule
                },
                onEditRequest = { schedule: Schedule ->
                    scheduleFormViewModel.onEditRequest(schedule = schedule)
                }
            )
        }

        if (formState.isVisible) {
            ManageScheduleForm(
                scheduleFormDataState,
                scheduleFormViewModel,
                project = project
            )
        }
    }

    @Composable
    // Display lazy column with Schedule cards
    private fun ScheduleContent(
        onDismissTooltip: () -> Unit,
        selectedSchedule: Schedule?,
        networkState: Resource<List<Schedule>>,
        onDeleteSchedule: (Schedule) -> Unit,
        onEditRequest: (Schedule) -> Unit
    ) {

        ProcessNetworkState(
            progressBarText = stringResource(id = R.string.loading_schedules),
            state = networkState
        ) { schedules: List<Schedule> ->
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(8.dp)
            ) {
                itemsIndexed(schedules) { _, schedule ->
                    DisplayScheduleItem(
                        schedule = schedule,
                        selectedSchedule = selectedSchedule,
                        onDismissTooltip = onDismissTooltip,
                        onDeleteSchedule = onDeleteSchedule,
                        onEditClickSchedule = onEditRequest
                    )
                }
            }
        }
    }

    @Composable
    private fun DisplayScheduleItem(
        schedule: Schedule,
        selectedSchedule: Schedule?,
        onDismissTooltip: () -> Unit,
        onDeleteSchedule: (Schedule) -> Unit,
        onEditClickSchedule: (Schedule) -> Unit
    ) {

        val progressExplanation =
            if (schedule.isDeadLIneExceed()) stringResource(
                id = R.string.deadline_exceeded_by,
                schedule.calculateOverdueMonths()
            ) else stringResource(
                id = R.string.work_covered_percentage, "${schedule.completionAsaPercentage}%"
            )

        Box(contentAlignment = Alignment.Center) {
            CustomScreenCard(
                tag = "Schedule",
                item = schedule,
                onDeleteClick = onDeleteSchedule,
                onEditClick = onEditClickSchedule,
                cardBodyContent = {
                    CardHorizontalBarGraph(
                        progress = schedule.completion,
                        progressBarColor = schedule.completionAsaPercentage,
                        progressBarText = "Elapsed ${formatFloat(schedule.progress)} months",
                        progressExplanation = progressExplanation,
                    )
                },
                hiddenContentItems = hiddenContentItems(schedule),
                canDelete = false,
                cardHeaderContent = { ScheduleItemHeaderContent(schedule) }
            )

            if (selectedSchedule != null && selectedSchedule.id == schedule.id) {
                Tooltip(
                    text = stringResource(
                        id = R.string.cannot_delete_schedule_directly,
                        "'${schedule.phase} Budget Phase.'"
                    )
                ) { onDismissTooltip() }
            }
        }
    }

    @Composable
    private fun ScheduleItemHeaderContent(schedule: Schedule) {
        Column {
            TitleText(text = schedule.phase, modifier = Modifier.align(Alignment.CenterHorizontally))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center, // Space items evenly
                verticalAlignment = Alignment.CenterVertically, // Align items vertically
            ) {
                SubtitleText(
                    text = "Remaining: ${schedule.calculateRemainingMonths()} months",
                )
            }
        }
    }

    @Composable
    private fun hiddenContentItems(schedule: Schedule): List<IconWithText> {
        return listOf(

            IconWithText(
                R.drawable.calendar_month_24px,
                "Start Date: ${schedule.startDate.isoToReadableDate()}"
            ),

            IconWithText(
                R.drawable.timelapse_24px, "Elapsed: ${(formatFloat(schedule.progress))} months"
            ),

            IconWithText(
                R.drawable.clock_24px, "Total Duration: ${schedule.totalDuration} months",
            ),

            IconWithText(
                R.drawable.hourglass_top_24px,
                "Deadline: ${schedule.getCompletionDate()}",
            ),
        )
    }
}