package com.teqie.taskmaster.ui.viewModel.budgetPhase

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.budget.BudgetPhase
import com.teqie.taskmaster.domain.model.budget.invoices.Invoice
import com.teqie.taskmaster.domain.useCases.budgetPhase.DeleteBudgetPhaseUseCase
import com.teqie.taskmaster.domain.useCases.budgetPhase.GetBudgetPhaseUseCase
import com.teqie.taskmaster.domain.useCases.budgetPhase.SyncBudgetPhasesToLocalUseCase
import com.teqie.taskmaster.ui.model.MessageType
import com.teqie.taskmaster.ui.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val getBudgetPhasesUseCase: GetBudgetPhaseUseCase,
    private val syncBudgetPhasesToLocalUseCase: SyncBudgetPhasesToLocalUseCase,
    private val deleteBudgetPhaseUseCase: DeleteBudgetPhaseUseCase
) : BaseViewModel() {

    private val _budgetState =
        MutableStateFlow<Resource<List<BudgetPhase>>>(Resource.Loading)
    val budgetState: StateFlow<Resource<List<BudgetPhase>>> = _budgetState

    private val _response =
        MutableStateFlow(Resource.Loading)
    val response: StateFlow<Resource<Unit>> = _response


    private val _invoicesState =
        MutableStateFlow<Resource<List<Invoice>>>(Resource.Loading)
    val invoicesState: StateFlow<Resource<List<Invoice>>> = _invoicesState

    fun syncBudgetPhasesToLocal(projectId: String) {
        viewModelScope.launch {
            syncBudgetPhasesToLocalUseCase(projectId).collect{
                Log.i("error", it.toString())
            }
        }
    }

    fun fetchBudgetPhases(projectId: String) {
        viewModelScope.launch {
            getBudgetPhasesUseCase(projectId).collect {
                _budgetState.value = it
            }
        }
    }

    fun deleteBudgetPhase(budgetPhaseId: String) {
        if (budgetPhaseId.isNotBlank()) {
            viewModelScope.launch {
                deleteBudgetPhaseUseCase(budgetPhaseId).collect {
                    processApiMessage(it) { messageType: MessageType, message: String ->
                        updateUiMessage(messageType, message)
                    }
                }
            }
        }
        hideConfirmDeleteDialog()
    }
}


