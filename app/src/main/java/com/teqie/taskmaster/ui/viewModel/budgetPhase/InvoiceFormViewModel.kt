package com.teqie.taskmaster.ui.viewModel.budgetPhase

import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.domain.mapper.UiInvoiceToDomainModel.toDomainModel
import com.teqie.taskmaster.domain.model.budget.invoices.CreateInvoiceRequest
import com.teqie.taskmaster.domain.model.budget.invoices.Invoice
import com.teqie.taskmaster.domain.useCases.budgetPhase.invoice.CreateBudgetInvoiceUseCase
import com.teqie.taskmaster.domain.useCases.budgetPhase.invoice.UpdateBudgetInvoiceUseCase
import com.teqie.taskmaster.ui.model.MessageType
import com.teqie.taskmaster.ui.viewModel.BaseFormViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoiceFormViewModel @Inject constructor(
    private val createBudgetInvoiceUseCase: CreateBudgetInvoiceUseCase,
    private val updateBudgetInvoiceUseCase: UpdateBudgetInvoiceUseCase
): BaseFormViewModel() {

    private val _invoiceFormState = MutableStateFlow(CreateInvoiceRequest())
    val invoiceFormState: StateFlow<CreateInvoiceRequest> = _invoiceFormState

    override fun createFormState() {
        viewModelScope.launch {
            createBudgetInvoiceUseCase(_invoiceFormState.value).collect {
                processApiMessage(it) { messageType: MessageType, message: String ->
                    updateUiMessage(messageType, message)
                }
            }
            handleSubmitBtnClick()
        }
    }

    fun selectedInvoice(selectedInvoice: Invoice){
        _invoiceFormState.value = selectedInvoice.toDomainModel()
    }

    override fun editFormState() {
        viewModelScope.launch {
            updateBudgetInvoiceUseCase(_invoiceFormState.value).collect {
                processApiMessage(it) { messageType: MessageType, message: String ->
                    updateUiMessage(messageType, message)
                }
            }

            handleSubmitBtnClick()
        }
    }

    override fun onIdChange(id: String) {
    }

    override fun clearForm(){
        _invoiceFormState.value = CreateInvoiceRequest()
    }

    fun onBudgetIdChange(budgetId: String){

        _invoiceFormState.update { it.copy(budgetId = budgetId) }
    }

    fun onAmountChange(amount: String){
        _invoiceFormState.update { it.copy(amount = amount) }
    }

    fun onDatePaidChange(date: String){
        _invoiceFormState.update { it.copy(date = date) }
    }

    fun onPaidChange(paid: String){
        _invoiceFormState.update { it.copy(paid = paid) }
    }

    fun handEditInvoiceRequest(invoice: Invoice){
        selectedInvoice(invoice)
        startEditing()
        showForm()
    }
}