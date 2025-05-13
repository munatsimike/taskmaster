package com.teqie.taskmaster.ui.screen.bugdetPhase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.teqie.taskmaster.R
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.budget.invoices.Invoice
import com.teqie.taskmaster.navigation.AppScreen
import com.teqie.taskmaster.ui.components.ConfirmDialog
import com.teqie.taskmaster.ui.components.factory.ButtonFactory.PrimaryButton
import com.teqie.taskmaster.ui.components.factory.TextFactory.TitleText
import com.teqie.taskmaster.ui.components.snackbar.CustomSnackbarHostState
import com.teqie.taskmaster.ui.components.snackbar.DisplaySnackBar
import com.teqie.taskmaster.ui.components.state.ProcessNetworkState
import com.teqie.taskmaster.ui.model.IconWithText
import com.teqie.taskmaster.ui.screen.BaseScreenWithFAB
import com.teqie.taskmaster.ui.screen.bugdetPhase.forms.ManageInvoiceForm
import com.teqie.taskmaster.ui.viewModel.SharedUserViewModel
import com.teqie.taskmaster.ui.viewModel.SharedViewModel
import com.teqie.taskmaster.ui.viewModel.auth.AuthViewModel
import com.teqie.taskmaster.ui.viewModel.budgetPhase.InvoiceFormViewModel
import com.teqie.taskmaster.ui.viewModel.invoice.InvoiceViewModel
import com.teqie.taskmaster.util.components.CardHorizontalBarGraph
import com.teqie.taskmaster.util.components.CustomRowWithAssignedTeamMember
import com.teqie.taskmaster.util.components.CustomScreenCard
import com.teqie.taskmaster.util.formatCurrency
import com.teqie.taskmaster.util.headerData
import com.teqie.taskmaster.util.isoToReadableDate

object Invoices {
    @Composable
    fun InvoiceScreen(
        budgetId: String,
        budgetPhase: String,
        navController: NavHostController,
        sharedViewModel: SharedViewModel,
        sharedUserViewModel: SharedUserViewModel,
        authViewModel: AuthViewModel,
        snackBarHostState: CustomSnackbarHostState,
        invoiceViewModel: InvoiceViewModel,
        invoiceFormViewModel: InvoiceFormViewModel = hiltViewModel(),
    ) {
        val uiScreenState by invoiceViewModel.screenState.collectAsState()
        val response by invoiceViewModel.invoicesState.collectAsState()
        val project by sharedViewModel.project.collectAsState()
        val loggedInUser by sharedUserViewModel.loggedInUser.collectAsState()
        val formUiState by invoiceFormViewModel.uiFormState.collectAsState()
        val message = invoiceFormViewModel.getServerResponseMsg(formUiState, uiScreenState)

        invoiceViewModel.handleActions(
            uiScreenState,
            formUiState
        ) { invoiceFormViewModel.toggleIsFormSubmitted() }

        LaunchedEffect(budgetId, uiScreenState.triggerFetch) {
            invoiceViewModel.syncInvoicesToLocalDb(budgetId)
            invoiceViewModel.fetchInvoices(budgetId)
        }

        DisplaySnackBar(
            uiMessage = message,
            customSnackbarHostState = snackBarHostState
        ) { invoiceFormViewModel.clearServerResponseMessage() }

        BaseScreenWithFAB(
            isFabVisible = uiScreenState.isFABVisible,
            fabBtnText = "Add invoice",
            onFabClick = { invoiceFormViewModel.showForm() },
            headerData = headerData(
                loggedInUser = loggedInUser,
                projectName = project.name,
                currentPage = AppScreen.BudgetInvoices.title,
                showBackBtn = true,
            ),
            onBackButtonClick = { navController.popBackStack() },
            onLogoutClick = { authViewModel.logout() }
        ) {
            InvoiceScreenContent(
                networkState = response,
                onEdit = { invoice: Invoice ->
                    invoiceFormViewModel.handEditInvoiceRequest(invoice)
                },
                onDelete = { invoice: Invoice ->
                    invoiceViewModel.handleDeleteItem("Invoice", invoice.id)
                },
                onNavigateToInvoiceFile = { invoiceId: String ->
                    navController.navigate(AppScreen.InvoicesFile.createRoute(invoiceId))
                },
                cardTag = "${budgetPhase.replaceFirstChar { it.uppercase() }} Invoice"
            )
        }

        if (formUiState.isVisible) {
            ManageInvoiceForm(
                budgetId = budgetId,
                invoiceFormViewModel = invoiceFormViewModel
            )
        }

        if (uiScreenState.deleteDialogState.isVisible) {
            ConfirmDialog(
                itemToDelete = uiScreenState.deleteDialogState.selectedItem.orEmpty(),
                onConfirm = {
                    invoiceViewModel.deleteInvoice(uiScreenState.deleteDialogState.selectedItemId)
                }) {
                invoiceViewModel.hideConfirmDeleteDialog()
            }
        }
    }
}

@Composable
private fun InvoiceScreenContent(
    cardTag: String,
    networkState: Resource<List<Invoice>>,
    onDelete: (Invoice) -> Unit,
    onEdit: (Invoice) -> Unit,
    onNavigateToInvoiceFile: (String) -> Unit
) {
    ProcessNetworkState(state = networkState) { invoices: List<Invoice> ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(invoices) { _, invoice ->
                InvoiceItem(
                    invoice = invoice,
                    onEditClick = {
                        onEdit(invoice)
                    },
                    onDeleteClick = {
                        onDelete(invoice)
                    },
                    onNavigateToInvoiceFile = onNavigateToInvoiceFile,
                    cardTag = cardTag
                )
            }
        }
    }
}

@Composable
private fun InvoiceItem(
    cardTag: String,
    invoice: Invoice,
    onEditClick: (Invoice) -> Unit,
    onDeleteClick: (Invoice) -> Unit,
    onNavigateToInvoiceFile: (String) -> Unit
) {
    CustomScreenCard(
        tag = cardTag,
        item = invoice,
        onDeleteClick = onDeleteClick,
        onEditClick = onEditClick,
        cardBodyContent = {
            CardHorizontalBarGraph(
                progress = invoice.progress,
                progressBarColor = invoice.progressAsAPercentage,
                progressBarText = "${invoice.progressAsAPercentage}% paid",
                progressExplanation = "Balance: " + formatCurrency(invoice.balance),
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                PrimaryButton(
                    width = 0.36f,
                    height = 40,
                    buttonText = "Pay invoice", onButtonClick = {},
                )
            }
        },
        hiddenContentItems = hiddenContentItems(
            invoice
        ),
        cardHeaderContent = { InvoiceCardHeaderContent(invoice, onNavigateToInvoiceFile) },
    )
}

@Composable
private fun InvoiceCardHeaderContent(
    invoice: Invoice,
    onNavigateToInvoiceFile: (String) -> Unit
) {
    Column {
        TitleText(
            text = "Paid ${formatCurrency(invoice.paid)} of ${formatCurrency(invoice.amount)}",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        invoice.assignedTeamMember.assignedToName?.let {
            CustomRowWithAssignedTeamMember(
                avaTaUrl = invoice.assignedTeamMember.assignedToAvatar,
                assignedTeamMemberName = it,
                buttonText = stringResource(id = R.string.file),
                btnIcon = R.drawable.document_24px
            ) { onNavigateToInvoiceFile(invoice.id) }
        }
    }
}

@Composable
private fun hiddenContentItems(
    invoice: Invoice
): List<IconWithText> {
    return listOf(
        IconWithText(
            R.drawable.calendar_month_24px, "Date: " + invoice.date.isoToReadableDate(), Color.Blue
        )
    )
}