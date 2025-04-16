package com.example.taskmaster.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.taskmaster.domain.useCases.project.GetProjectDashboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor( private val getProjectDashboardUseCase: GetProjectDashboardUseCase) :  ViewModel(){
   fun getDashBoardData(id: String) = getProjectDashboardUseCase(id)
}
