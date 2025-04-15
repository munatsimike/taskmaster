package com.example.taskmaster.ui.viewModel.projects

import androidx.lifecycle.viewModelScope
import com.example.taskmaster.data.remote.api.NetworkResponse
import com.example.taskmaster.domain.model.project.Project
import com.example.taskmaster.domain.useCases.project.AddEditProjectUseCase
import com.example.taskmaster.ui.viewModel.BaseFormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectFormViewModel @Inject constructor(
    private val addEditProjectUseCase: AddEditProjectUseCase,
) : BaseFormViewModel() {

    private val _pojectState = MutableStateFlow(Project())
    val projectState: StateFlow<Project> = _pojectState

    fun onSelectedProjectChange(project: Project) {
        _pojectState.value = project
    }

    override fun createFormState() {
        createOrEditProject(false)
    }

    override fun editFormState() {
        createOrEditProject(true)
    }

    override fun onIdChange(id: String) {
    }

    fun onNameChange(name: String) {
        _pojectState.update { it.copy(name = name) }
    }

    fun onDescriptionChange(description: String) {
        _pojectState.update { it.copy(description = description) }
    }

    fun onAddressChange(address: String) {
        _pojectState.update { it.copy(address = address) }
    }

    fun onImageUrlChange(url: String?) {
        _pojectState.update { it.copy(thumbnailUrl = url) }
    }

    override fun clearForm() {
        _pojectState.value = Project()
    }

    fun handleEdit(project: Project){
        onSelectedProjectChange(project)
       showEditForm()
    }

    private fun createOrEditProject(isEditing: Boolean){
        val newProject = _pojectState.value
        if (newProject.isValid()) {
            viewModelScope.launch {
                val response = addEditProjectUseCase(newProject, isEditing)
                response.collect { apiResponse ->
                    handleResponse(apiResponse)
                }
                handleSubmitBtnClick()
            }
        } else {
            NetworkResponse.Error(Exception("Name and description cannot be empty"))
        }
    }
}