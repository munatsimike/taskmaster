package com.example.taskmaster.ui.viewModel.projects

import androidx.lifecycle.viewModelScope
import com.example.taskmaster.data.remote.api.NetworkResponse
import com.example.taskmaster.domain.model.project.Project

import com.example.taskmaster.domain.useCases.project.AddEditProjectUseCase
import com.example.taskmaster.ui.viewModel.BaseFormViewModel
import com.example.taskmaster.ui.viewModel.UiInteractionViewModel
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

    private val _pojectFormState = MutableStateFlow(Project())
    val addEditProjectFormatState: StateFlow<Project> = _pojectFormState

    fun onSelectedChange(project: Project) {
        _pojectFormState.value = project
    }

    override fun createFormState() {
        val newProject = _pojectFormState.value
        if (newProject.isValid()) {
            viewModelScope.launch {
                val response = addEditProjectUseCase(newProject)
                response.collect { apiResponse ->
                    handleResponse(apiResponse)
                }
                handleSubmitBtnClick()
            }
        } else {
            NetworkResponse.Error(Exception("Name and description cannot be empty"))
        }
    }

    override fun editFormState() {
        createFormState()
    }

    fun onEnableEditing() {
        _pojectFormState.update { it.copy(isEditing = true) }
    }

    override fun onIdChange(id: String) {
    }

    fun onNameChange(name: String) {
        _pojectFormState.update { it.copy(name = name) }
    }

    fun onDescriptionChange(description: String) {
        _pojectFormState.update { it.copy(description = description) }
    }

    fun onAddressChange(address: String) {
        _pojectFormState.update { it.copy(address = address) }
    }

    fun onImageUrlChange(url: String?) {
        _pojectFormState.update { it.copy(thumbnailUrl = url) }
    }

    override fun clearForm() {
        _pojectFormState.value = Project()
    }

    fun handleEdit(project: Project){
        onSelectedChange(project)
       showEditForm()
    }
}