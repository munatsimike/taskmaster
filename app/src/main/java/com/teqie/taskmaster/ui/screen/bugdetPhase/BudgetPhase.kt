package com.teqie.taskmaster.ui.screen.bugdetPhase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.navigation.NavController
import com.teqie.taskmaster.R
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.budget.BudgetPhase
import com.teqie.taskmaster.navigation.AppScreen
import com.teqie.taskmaster.ui.components.ConfirmDialog
import com.teqie.taskmaster.ui.components.factory.TextFactory.TitleText
import com.teqie.taskmaster.ui.components.snackbar.CustomSnackbarHostState
import com.teqie.taskmaster.ui.components.snackbar.DisplaySnackBar
import com.teqie.taskmaster.ui.components.state.ProcessNetworkState
import com.teqie.taskmaster.ui.model.IconWithText
import com.teqie.taskmaster.ui.screen.BaseScreenWithFAB
import com.teqie.taskmaster.ui.screen.bugdetPhase.forms.ManageBudgetPhaseForm
import com.teqie.taskmaster.ui.viewModel.SharedUserViewModel
import com.teqie.taskmaster.ui.viewModel.SharedViewModel
import com.teqie.taskmaster.ui.viewModel.auth.AuthViewModel
import com.teqie.taskmaster.ui.viewModel.budgetPhase.BudgetFormViewModel
import com.teqie.taskmaster.ui.viewModel.budgetPhase.BudgetViewModel
import com.teqie.taskmaster.util.components.CardHorizontalBarGraph
import com.teqie.taskmaster.util.components.CustomRowWithAssignedTeamMember
import com.teqie.taskmaster.util.components.CustomScreenCard
import com.teqie.taskmaster.util.formatCurrency
import com.teqie.taskmaster.util.headerData
import kotlin.math.abs

object BudgetPhase {

    @Composable
    fun BudgetPhaseMainScreen(
        navController: NavController,
        sharedViewModel: SharedViewModel,
        sharedUserViewModel: SharedUserViewModel,
        authViewModel: AuthViewModel,
        snackBarHostState: CustomSnackbarHostState,
        budgetPhaseViewModel: BudgetViewModel,
        budgetFormViewModel: BudgetFormViewModel = hiltViewModel()
    ) {
        val project by sharedViewModel.project.collectAsState()
        val loggedInUser by sharedUserViewModel.loggedInUser.collectAsState()
        val formState by budgetFormViewModel.uiFormState.collectAsState()
        val uiScreenState by budgetPhaseViewModel.screenState.collectAsState()
        val budgets by budgetPhaseViewModel.budgetState.collectAsState()

        budgetPhaseViewModel.handleActions(
            uiScreenState,
            formState
        ) { budgetFormViewModel.toggleIsFormSubmitted() }

        DisplaySnackBar(
            uiMessage = uiScreenState.message,
            customSnackbarHostState = snackBarHostState
        ) { budgetFormViewModel.clearUiMessage(formState, uiScreenState) }

        LaunchedEffect(project.id, uiScreenState.triggerFetch) {
            budgetPhaseViewModel.syncBudgetPhasesToLocal(project.id)
            budgetPhaseViewModel.fetchBudgetPhases(projectId = project.id)
        }

        BaseScreenWithFAB(
            isFabVisible = uiScreenState.isFABVisible,
            fabBtnText = stringResource(id = R.string.add_budget),
            onFabClick = { budgetFormViewModel.showForm() },
            headerData = headerData(
                loggedInUser = loggedInUser,
                projectName = project.name,
                currentPage = AppScreen.Budget.title + " phases"
            ),
            onLogoutClick = { authViewModel.logout() },
            onBackButtonClick = { navController.popBackStack() }
        ) {
            BudgetPhaseContent(budgets,
                onNavigateToInvoices = { budgetId: String, budgetPhase: String ->
                    navController.navigate(
                        AppScreen.BudgetInvoices.createRoute(
                            budgetId, budgetPhase
                        )
                    )
                },
                onEditBudgetPhase = { budgetPhase: BudgetPhase ->
                    budgetFormViewModel.editBudgetPhaseRequest(budgetPhase)
                },
                onDeletePhase = { budgetPhase: BudgetPhase ->
                    budgetPhaseViewModel.handleDeleteItem(budgetPhase.phase, budgetPhase.id)
                },
                fabVisibility = { isVisible: Boolean ->
                    budgetPhaseViewModel.setFBVisibility(isVisible = isVisible)
                }
            )
        }

        if (uiScreenState.deleteDialogState.isVisible) {
            ConfirmDialog(
                itemToDelete = uiScreenState.deleteDialogState.selectedItem.orEmpty(),
                onConfirm = {
                    uiScreenState.deleteDialogState.selectedItemId?.let {
                        budgetPhaseViewModel.deleteBudgetPhase(
                            it
                        )
                    }
                })
            {
                budgetPhaseViewModel.hideConfirmDeleteDialog()
            }
        }

        if (formState.isVisible) {
            ManageBudgetPhaseForm(
                projectId = project.id,
                formState = formState,
                budgetFormViewModel = budgetFormViewModel
            )
        }
    }
}

@Composable
private fun BudgetPhaseContent(
    networkState: Resource<List<BudgetPhase>>,
    fabVisibility: (Boolean) -> Unit,
    onNavigateToInvoices: (String, String) -> Unit,
    onEditBudgetPhase: (BudgetPhase) -> Unit,
    onDeletePhase: (BudgetPhase) -> Unit
) {
    ProcessNetworkState(
        state = networkState,
        fabVisibility = fabVisibility,
        progressBarText = stringResource(id = R.string.loading_budget_phase),
    ) { budgetPhases: List<BudgetPhase> ->
        LazyColumn(Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(budgetPhases) { _, budgetPhase ->
                BudgetPhaseItem(
                    budgetPhase = budgetPhase,
                    onNavigateToInvoices = onNavigateToInvoices,
                    onEditClickBudgetPhase = onEditBudgetPhase,
                    onDeletePhase = onDeletePhase
                )
            }
        }
    }
}

@Composable
private fun BudgetPhaseItem(
    budgetPhase: BudgetPhase,
    onNavigateToInvoices: (String, String) -> Unit,
    onEditClickBudgetPhase: (BudgetPhase) -> Unit,
    onDeletePhase: (BudgetPhase) -> Unit
) {
    CustomScreenCard(
        tag = "Budget phase",
        item = budgetPhase,
        onDeleteClick = onDeletePhase,
        onEditClick = onEditClickBudgetPhase,
        cardBodyContent = {
            CardHorizontalBarGraph(
                progress = budgetPhase.budgetSpent,
                progressBarColor = budgetPhase.budgetSpentPercentage,
                progressBarText = stringResource(
                    id = R.string.total_budget,
                    formatCurrency(budgetPhase.newBudget)
                ),
                progressExplanation = budgetExplanation(budgetPhase = budgetPhase),
            )
        },
        hiddenContentItems = hiddenContentItems(budgetPhase),
        cardHeaderContent = { BudgetPhaseCardHeaderContent(budgetPhase, onNavigateToInvoices) },
    )
}

@Composable
private fun BudgetPhaseCardHeaderContent(
    budgetPhase: BudgetPhase, onNavigateToInvoices: (String, String) -> Unit
) {
    Column {
        TitleText(text = budgetPhase.phase, modifier = Modifier.align(Alignment.CenterHorizontally))
        budgetPhase.assignedTeamMember.assignedToName?.let {
            CustomRowWithAssignedTeamMember(
                avaTaUrl = budgetPhase.assignedTeamMember.assignedToAvatar,
                assignedTeamMemberName = it,
                buttonText = stringResource(id = R.string.invoices),
            ) { onNavigateToInvoices(budgetPhase.id, budgetPhase.phase) }
        }
    }
}

@Composable
private fun budgetExplanation(budgetPhase: BudgetPhase): String {
    return when {
        budgetPhase.budgetSpentPercentage == 0 -> stringResource(id = R.string.no_money_spent)
        budgetPhase.budgetSpentPercentage > 100 -> stringResource(
            id = R.string.budget_exceeded,
            formatCurrency(budgetPhase.totalAmount - budgetPhase.newBudget)
        )// Assuming you have an amount to display
        else -> "${budgetPhase.budgetSpentPercentage}% of budget spent [${formatCurrency(budgetPhase.totalAmount.toDouble())}]"
    }
}

private fun hiddenContentItems(budgetPhase: BudgetPhase): List<IconWithText> {
    val pendingDue = (budgetPhase.totalAmount - budgetPhase.totalPaid).toDouble()
    val remainingBalance = budgetPhase.newBudget - budgetPhase.totalAmount

    val formatedBudgetTxt = "Revised BudgetPhase: ${
        formatCurrency(budgetPhase.newBudget)
    }"
    val formatedPaidToDate =
        " Paid to date: ${formatCurrency(budgetPhase.totalPaid.toDouble())}"
    val formatedInitBudgetTxt = " Initial budget: ${
        formatCurrency(budgetPhase.initialBudget)
    }"

    val formatedPendingDueTxt = " Pending due: ${
        formatCurrency(pendingDue)
    }"

    val formatedRemainingTxt = " Remaining balance: ${
        formatCurrency(abs(remainingBalance))
    }"

    return listOf(

        IconWithText(
            R.drawable.paid_24px, formatedPaidToDate, Color.Blue
        ),

        IconWithText(
            R.drawable.paid_24px, formatedPendingDueTxt, Color.Red
        ),

        IconWithText(
            R.drawable.paid_24px, formatedRemainingTxt, Color(0xFF093F0B)
        ),

        IconWithText(
            R.drawable.money_bag_24px, formatedInitBudgetTxt, Color(0xFF616161)
        ),

        IconWithText(
            R.drawable.money_bag_24px, formatedBudgetTxt, Color(0xFF9B59B6)
        ),
    )
}