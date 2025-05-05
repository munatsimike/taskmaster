package com.teqie.taskmaster.ui.screen.bugdetPhase.forms

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import com.teqie.taskmaster.domain.model.budget.invoices.CreateInvoiceRequest
import com.teqie.taskmaster.ui.components.FormModal
import com.teqie.taskmaster.ui.components.factory.ButtonFactory.PrimaryButton
import com.teqie.taskmaster.ui.components.factory.TextFieldFactory.FilledTextField
import com.teqie.taskmaster.ui.components.form.CustomDatePicker
import com.teqie.taskmaster.ui.viewModel.budgetPhase.InvoiceFormViewModel


@Composable
fun ManageInvoiceForm(
    budgetId: String,
    invoiceFormViewModel: InvoiceFormViewModel
) {
    val formUiState by invoiceFormViewModel.uiFormState.collectAsState()
    val formState by invoiceFormViewModel.invoiceFormState.collectAsState()

    FormModal(formContent = {
        AddInvoiceFormContent(
            formDataState = formState,
            budgetId = budgetId,
            isEditing = formUiState.isEditing,
            invoiceFormViewModel = invoiceFormViewModel,
            onTotalAmountChange = invoiceFormViewModel::onAmountChange,
            onPaidChange = invoiceFormViewModel::onPaidChange,
            onPaidDateChange = invoiceFormViewModel::onDatePaidChange,
            onEditInvoice = {
                invoiceFormViewModel.editFormState()
            },
            onCreateInvoice = {
                invoiceFormViewModel.createFormState()
            })
    }) {
        invoiceFormViewModel.closeForm()
    }
}

@Composable
private fun AddInvoiceFormContent(
    formDataState: CreateInvoiceRequest,
    budgetId: String,
    isEditing: Boolean,
    invoiceFormViewModel: InvoiceFormViewModel,
    onTotalAmountChange: (String) -> Unit,
    onPaidChange: (String) -> Unit,
    onPaidDateChange: (String) -> Unit,
    onEditInvoice: () -> Unit,
    onCreateInvoice: () -> Unit
) {
    val createInvoiceTxt = "Create Invoice"
    val editInvoiceTxt = "Edit Invoice"

    Text(
        text = if (isEditing) editInvoiceTxt else createInvoiceTxt,
        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp)
    )

    FilledTextField(
        formDataState.amount,
        "Total Amount", onValueChange =
        { newValue -> onTotalAmountChange(newValue) })

    FilledTextField(
        formDataState.paid,
        "Paid", onValueChange =
        { newValue -> onPaidChange(newValue) })

    CustomDatePicker(
        formDataState.date,
        labelTxt = "Paid on",
    ) { date ->
        onPaidDateChange(date)
    }

    PrimaryButton(
        buttonText = if (isEditing) editInvoiceTxt else createInvoiceTxt,
        onButtonClick = {
            invoiceFormViewModel.onBudgetIdChange(budgetId)
            if (isEditing) {
                onEditInvoice()
            } else {
                onCreateInvoice()
            }
        })

}


