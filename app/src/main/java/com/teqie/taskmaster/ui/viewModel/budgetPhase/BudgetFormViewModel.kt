package com.teqie.taskmaster.ui.viewModel.budgetPhase

import androidx.lifecycle.viewModelScope

import com.teqie.taskmaster.domain.model.budget.BudgetPhase
import com.teqie.taskmaster.domain.model.budget.BudgetPhaseFormData
import com.teqie.taskmaster.domain.useCases.budgetPhase.CreateNewBudgetUseCase
import com.teqie.taskmaster.domain.useCases.budgetPhase.UpdateBudgetPhaseUseCase
import com.teqie.taskmaster.ui.model.BudgetFormError
import com.teqie.taskmaster.ui.model.MessageType
import com.teqie.taskmaster.ui.viewModel.BaseFormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetFormViewModel @Inject constructor(
    private val createNewBudgetUseCase: CreateNewBudgetUseCase,
    private val updateBudgetPhaseUseCase: UpdateBudgetPhaseUseCase
) : BaseFormViewModel() {

    private val _newBudgetState = MutableStateFlow(BudgetPhaseFormData())
    val newBudgetState: StateFlow<BudgetPhaseFormData> = _newBudgetState

    override fun createFormState() {
        val errors = _newBudgetState.value.validateBudgetForm()
        if (errors.isEmpty()) {
            viewModelScope.launch {
                createNewBudgetUseCase(
                    _newBudgetState.value
                ).collect {
                    processApiMessage(it) { messageType: MessageType, message: String ->
                        updateUiMessage(messageType, message)
                    }
                }
            }
        } else {
            _uiFormState.update {
                it.copy(formValidationErrors = errors.associate { error ->
                    error.key to mapErrorToMessage(error)
                })
            }
        }
    }

    override fun editFormState() {
        viewModelScope.launch {
            updateBudgetPhaseUseCase(_newBudgetState.value).collect { apiResponse ->
                processApiMessage(apiResponse) { messageType: MessageType, message: String ->
                    updateUiMessage(messageType, message)
                    handleSubmitBtnClick()
                }
            }
        }
    }

    fun editBudgetPhaseRequest(budgetPhase: BudgetPhase) {
       // onBudgetPhaseChange(budgetPhase) // Update the phase in the state
        showEditForm()           // Display the form
    }

    override fun onIdChange(id: String) {
        _newBudgetState.update { it.copy(projectId = id) }
    }

    override fun clearForm() {
        _newBudgetState.value = BudgetPhaseFormData()
        if (_uiFormState.value.formValidationErrors.isNotEmpty()) _uiFormState.update {
            it.copy(
                formValidationErrors = emptyMap()
            )
        }
    }

   /** private fun onBudgetPhaseChange(budgetPhase: BudgetPhase) {
        _newBudgetState.value = budgetPhase.toBudgetPhaseFormData()
    }**/

    fun onPhaseChange(phase: String) {
        _newBudgetState.update { it.copy(phase = phase) }
    }

    fun onStartDateChange(date: String) {
        _newBudgetState.update { it.copy(startDate = date) }
    }

    fun onDurationChange(duration: String) {
        _newBudgetState.update { it.copy(totalDuration = duration) }
    }

    fun onProgressChange(progress: String) {
        _newBudgetState.update { it.copy(progress = progress) }
    }

    fun onBudgetChange(budget: String) {
        _newBudgetState.update { it.copy(initBudget = budget) }
    }

    fun onRevisedBudgetChange(budget: String) {
        _newBudgetState.update { it.copy(revisedBudget = budget) }
    }

    private fun onAssignedUserChange(assignedTo: String) {
        _newBudgetState.update { it.copy(assignedToName = assignedTo) }
    }

    private fun onAssignedUserIdChange(userId: String) {
        _newBudgetState.update { it.copy(userId = userId) }
    }

    fun onSelectedUser(userName: String, userId: String) {
        onAssignedUserChange(userName)
        onAssignedUserIdChange(userId)
        this.collapseDropDownMenu()
    }

    fun handleOnCreateBudgetPhase(projectId: String) {
        onIdChange(projectId)
        createFormState()
        handleSubmitBtnClick()
    }

    private fun mapErrorToMessage(error: BudgetFormError): String {
        return when (error) {
            BudgetFormError.EmptyPhase -> "Phase cannot be empty."
            BudgetFormError.EmptyBudget -> "BudgetPhase cannot be empty."
            BudgetFormError.InvalidTotalDuration -> "Total duration must be a positive number."
            BudgetFormError.InvalidProgress -> "Progress cannot be less than 0."
            BudgetFormError.EmptyUser -> "Please select a user"
        }
    }
}