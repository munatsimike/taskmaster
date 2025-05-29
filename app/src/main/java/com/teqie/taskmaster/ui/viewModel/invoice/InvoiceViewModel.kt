package com.teqie.taskmaster.ui.viewModel.invoice

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.InvoiceFile
import com.teqie.taskmaster.domain.model.budget.invoices.Invoice
import com.teqie.taskmaster.domain.useCases.budgetPhase.invoice.DeleteInvoiceUseCase
import com.teqie.taskmaster.domain.useCases.budgetPhase.invoice.GetInvoiceFilesUseCase
import com.teqie.taskmaster.domain.useCases.budgetPhase.invoice.GetInvoiceUseCase
import com.teqie.taskmaster.domain.useCases.budgetPhase.invoice.SyncInvoicesToLocalDbUseCase
import com.teqie.taskmaster.ui.model.MessageType
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
    private val syncInvoicesToLocalDbUseCase: SyncInvoicesToLocalDbUseCase
) : BaseViewModel() {

    private val _budgetInvoiceFile =
        MutableStateFlow<Resource<List<InvoiceFile>>>(Resource.Loading)
    val budgetInvoiceFile: StateFlow<Resource<List<InvoiceFile>>> = _budgetInvoiceFile

    private val _invoicesState =
        MutableStateFlow<Resource<List<Invoice>>>(Resource.Loading)
    val invoicesState: StateFlow<Resource<List<Invoice>>> = _invoicesState


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

    fun fetchInvoices(budgetId: String) {
        viewModelScope.launch {
            getInvoicesUseCase(budgetId).collect { response ->
                _invoicesState.value = response
            }
        }
    }

    fun syncInvoicesToLocalDb(budgetId: String) {
        viewModelScope.launch {
            syncInvoicesToLocalDbUseCase(budgetId).collect {
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
}