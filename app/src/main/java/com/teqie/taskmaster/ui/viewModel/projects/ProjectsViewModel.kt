package com.teqie.taskmaster.ui.viewModel.projects


import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.data.remote.api.Resource
import com.teqie.taskmaster.domain.model.project.Project
import com.teqie.taskmaster.domain.useCases.project.DeleteProjectUseCase
import com.teqie.taskmaster.domain.useCases.project.GetProjectsUseCase
import com.teqie.taskmaster.domain.useCases.project.UpdateProjectsUseCase
import com.teqie.taskmaster.ui.model.MessageType
import com.teqie.taskmaster.ui.viewModel.UiInteractionViewModel
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
    private val updateProjectsUseCase: UpdateProjectsUseCase
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

    fun updateProjects() {
        viewModelScope.launch {
            updateProjectsUseCase().collectLatest { msg ->
                when (msg) {
                    is Resource.Error -> {
                        updateUiMessage(messageType = MessageType.ERROR, msg.exception.message)
                    }
                    is Resource.Failure -> {
                        updateUiMessage(messageType = MessageType.ERROR, msg.message)
                    }
                    else -> {} // ignore everything else
                }
            }
        }
    }
}
