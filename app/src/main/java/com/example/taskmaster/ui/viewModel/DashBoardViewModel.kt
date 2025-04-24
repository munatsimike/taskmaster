package com.example.taskmaster.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.domain.useCases.dashboard.FetchDashboardUseCase
import com.example.taskmaster.domain.useCases.dashboard.UpdateDashboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val fetchDashboardUseCase: FetchDashboardUseCase,
    private val updateDashboardUseCase: UpdateDashboardUseCase
) : ViewModel() {
    fun fetchDashBoardData(id: String) = fetchDashboardUseCase(id)

    fun updateDashboardData(projectId: String){
        viewModelScope.launch {
             updateDashboardUseCase(projectId).collect{}
        }
    }
}
