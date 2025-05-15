package com.teqie.taskmaster.ui.viewModel.schedule

import androidx.lifecycle.viewModelScope
import com.example.taskflow.data.remote.api.NetworkResponse
import com.example.taskflow.domain.model.Schedule
import com.example.taskflow.domain.usecases.get.GetScheduleUseCase
import com.example.taskflow.ui.viewModel.BaseViewModel
import com.teqie.taskmaster.domain.model.Schedule
import com.teqie.taskmaster.ui.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getScheduleUseCase: GetScheduleUseCase,
) :
    BaseViewModel() {

    private val _scheduleState =
        MutableStateFlow<NetworkResponse<List<Schedule>>>(NetworkResponse.Loading)
    val scheduleState: StateFlow<NetworkResponse<List<Schedule>>> = _scheduleState

    fun getSchedule(projectId: String) {
        viewModelScope.launch {
            getScheduleUseCase(projectId).collect { networkResponse ->
                _scheduleState.value = networkResponse
            }
        }
    }
}