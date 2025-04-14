package com.example.taskmaster.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.taskmaster.domain.model.project.Project
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor(): ViewModel() {

    private val _Existing_project = MutableStateFlow(Project())
    val project: StateFlow<Project> = _Existing_project

    fun setProject(project: Project){
        _Existing_project.value = project
    }
}