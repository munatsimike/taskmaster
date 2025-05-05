package com.teqie.taskmaster.ui.viewModel.budgetPhase

import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.useCases.budgetPhase.invoice.DeleteInvoiceUseCase
import com.teqie.taskmaster.domain.useCases.budgetPhase.invoice.GetInvoiceFilesUseCase
import com.teqie.taskmaster.domain.useCases.budgetPhase.invoice.GetInvoiceUseCase
import com.teqie.taskmaster.ui.model.MessageType
import com.teqie.taskmaster.ui.screen.bugdetPhase.InvoiceFile
import com.teqie.taskmaster.ui.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoiceViewModel @Inject constructor(
    private val getInvoicesUseCase: GetInvoiceUseCase,
    private val getInvoiceFilesUseCase: GetInvoiceFilesUseCase,
    private val deleteInvoiceUseCase: DeleteInvoiceUseCase,
) : BaseViewModel() {

    private val _invoicesState =
        MutableStateFlow<Resource<Unit>>(Resource.Loading)
    val invoicesState: StateFlow<Resource<Unit>> = _invoicesState

    private val _budgetInvoiceFile =
        MutableStateFlow<Resource<List<InvoiceFile>>>(Resource.Loading)
    val budgetInvoiceFile: StateFlow<Resource<List<InvoiceFile>>> = _budgetInvoiceFile


    fun fetchInvoices(budgetId: String) {
        viewModelScope.launch {
            getInvoicesUseCase(budgetId).collect { response ->
                _invoicesState.value = response
            }
        }
    }

    // fetch all payment records for a particular project
    fun getAllInvoiceFiles(invoiceId: String) {
        viewModelScope.launch {
            getInvoiceFilesUseCase(invoiceId).collect {
                _budgetInvoiceFile.value = it
            }
        }
    }

    fun deleteInvoice(invoiceId: String?) {
        viewModelScope.launch {
            if (invoiceId != null) {
                deleteInvoiceUseCase(invoiceId).collect {
                    processApiMessage(it) { messageType: MessageType, message: String ->
                        updateUiMessage(messageType, message)
                    }
                }
            }
            hideConfirmDeleteDialog()
        }
    }
}