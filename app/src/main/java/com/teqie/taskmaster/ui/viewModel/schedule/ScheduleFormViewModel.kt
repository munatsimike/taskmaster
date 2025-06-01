package com.teqie.taskmaster.ui.viewModel.schedule

import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.domain.model.Schedule
import com.teqie.taskmaster.domain.useCases.schedule.UpdateScheduleUseCase
import com.teqie.taskmaster.ui.screen.schedule.ScheduleFormState
import com.teqie.taskmaster.ui.viewModel.BaseFormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleFormViewModel @Inject constructor(
    private val updateScheduleUseCase: UpdateScheduleUseCase,
) : BaseFormViewModel() {

    private val _scheduleFormDataState = MutableStateFlow(ScheduleFormState())
    val scheduleFormDataState: StateFlow<ScheduleFormState> = _scheduleFormDataState

    override fun createFormState() {
    }

    override fun editFormState() {
    }

    override fun onIdChange(id: String) {
    }

    override fun clearForm() {
        _scheduleFormDataState.value = ScheduleFormState()
    }

    fun onSaveChanges() {
        viewModelScope.launch {
            updateScheduleUseCase(_scheduleFormDataState.value).collect { response ->
                handleResponse(response)
                handleSubmitBtnClick()
            }
        }
    }

    fun onEditRequest(schedule: Schedule) {
        initializeFormState(schedule)
        startEditing()
        showForm()
    }

    fun onStartDateChange(date: String) {
        _scheduleFormDataState.update { it.copy(startDate = date) }
    }

    // Handle changes to the total duration
    fun onDurationChange(newDuration: String) {
        _scheduleFormDataState.update { it.copy(totalDuration = newDuration) }
    }

    // Handle changes to the progress
    fun onProgressChange(newProgress: String) {
        _scheduleFormDataState.update { it.copy(progress = newProgress) }
    }

    private fun initializeFormState(schedule: Schedule) {
        _scheduleFormDataState.update {
            it.copy(
                id = schedule.id,
                startDate = schedule.startDate,
                totalDuration = schedule.totalDuration.toString(),
                progress = schedule.progress.toString(),
                phase = schedule.phase
            )
        }
    }
}