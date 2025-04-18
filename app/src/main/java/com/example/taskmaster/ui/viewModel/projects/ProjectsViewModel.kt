package com.example.taskmaster.ui.viewModel.projects


import androidx.lifecycle.viewModelScope
import com.example.taskmaster.data.remote.api.Resource
import com.example.taskmaster.domain.model.project.Project
import com.example.taskmaster.domain.useCases.project.DeleteProjectUseCase
import com.example.taskmaster.domain.useCases.project.GetProjectsUseCase
import com.example.taskmaster.ui.viewModel.UiInteractionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor(
    private val getProjectsUseCase: GetProjectsUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
) : UiInteractionViewModel() {

    private val _projects =
        MutableStateFlow<Resource<List<Project>>>(Resource.Loading)
    val projects: StateFlow<Resource<List<Project>>> = _projects

    fun deleteProject(projectId: String?) {
        if (projectId != null) {
            viewModelScope.launch {
                deleteProjectUseCase(projectId).collectLatest {
                    handleResponse(it)
                }
                hideConfirmDeleteDialog()
            }
        }
    }

    fun getAllProjects() {
        viewModelScope.launch {
            getProjectsUseCase().collectLatest { projects ->
                _projects.value = projects
            }
        }
    }
}
