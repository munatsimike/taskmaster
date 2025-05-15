package com.teqie.taskmaster.ui.viewModel.schedule

import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.Schedule
import com.teqie.taskmaster.domain.useCases.schedule.GetScheduleUseCase
import com.teqie.taskmaster.domain.useCases.schedule.SyncScheduleToLocalUseCase
import com.teqie.taskmaster.domain.useCases.schedule.UpdateScheduleUseCase
import com.teqie.taskmaster.ui.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getScheduleUseCase: GetScheduleUseCase,
    private val syncScheduleToLocalUseCase: SyncScheduleToLocalUseCase,
    private val updateScheduleUseCase: UpdateScheduleUseCase
) :
    BaseViewModel() {

    private val _scheduleState =
        MutableStateFlow<Resource<List<Schedule>>>(Resource.Loading)
    val scheduleState: StateFlow<Resource<List<Schedule>>> = _scheduleState

    fun getSchedule(projectId: String) {
        viewModelScope.launch {
            getScheduleUseCase(projectId).collect { networkResponse ->
                _scheduleState.value = networkResponse
            }
        }
    }

    fun syncScheduleToLocalDb(projectId: String){
        viewModelScope.launch {
            syncScheduleToLocalUseCase(projectId).collect{
            }
        }
    }
}