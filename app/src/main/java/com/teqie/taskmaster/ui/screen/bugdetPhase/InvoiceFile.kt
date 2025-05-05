package com.teqie.taskmaster.ui.screen.bugdetPhase

object InvoiceFile {

}
/**
    @Composable
    fun InvoiceFileMainScreen(
        invoiceId: String,
        navController: NavController,
        sharedViewModel: SharedViewModel,
        sharedUserViewModel: SharedUserViewModel,
        authViewModel: AuthViewModel,
        snackbarHostState: CustomSnackbarHostState,
        budgetViewModel: BudgetViewModel,
        fileViewModel: FileManagementViewModel,
        fileFormViewmodel: FileFormManagementViewModel,

        ) {
        val screenState by fileViewModel.screenState.collectAsState()
        val allInvoices by budgetViewModel.budgetInvoiceFile.collectAsState()
        val project by sharedViewModel.project.collectAsState()
        val loggedInUser by sharedUserViewModel.loggedInUser.collectAsState()
        val formUiState by fileFormViewmodel.uiFormState.collectAsState()

        val message = fileViewModel.getServerResponseMsg(formUiState, screenState)

        fileViewModel.handleActions(
            screenState,
            formUiState
        ) { fileFormViewmodel.toggleIsFormSubmitted() }

        DisplaySnackBar(
            uiMessage = message,
            customSnackbarHostState = snackbarHostState
        ) {
            fileViewModel.clearUiScreenStateMessage(uiScreenState = screenState)
        }

        LaunchedEffect(invoiceId, screenState.triggerFetch) {
            budgetViewModel.getAllInvoiceFiles(invoiceId)
        }

        BaseScreenWithFAB(
            onFabClick = {
                fileFormViewmodel.handleOnAddFileClick(
                    FileType.InvoiceFile,
                    invoiceId
                )
            },
            fabBtnText = "Add file",
            isFabVisible = screenState.isFABVisible,
            headerData = headerData(
                loggedInUser = loggedInUser,
                projectName = project.name,
                currentPage = AppScreen.InvoicesFile.title,
                showBackBtn = true
            ),
            onBackButtonClick = { navController.popBackStack() },
            onLogoutClick = { authViewModel.logout() }
        ) {
            DisplayFiles(
                checkedIds = screenState.selectedItemIds,
                networkState = allInvoices,
                onCheckedChange = fileViewModel::onIsCheckedChange,
                onDeleteClick = { invoice: InvoiceFile ->
                    fileViewModel.handleDeleteItem("File", invoice.id)
                },
                onEdit = { file: InvoiceFile ->
                    fileFormViewmodel.handleOnEditFileClick(file)
                }
            )
        }

        if (screenState.deleteDialogState.isVisible) {
            ConfirmDialog(itemToDelete = "Invoice file", onConfirm = {
                fileViewModel.onDeleteFile(
                    screenState.deleteDialogState.selectedItemId,
                    FileType.InvoiceFile
                )
            }) {
                fileViewModel.hideConfirmDeleteDialog()
            }
        }

        if (formUiState.isVisible) {
            FormModal(formContent = {
                FileManagementForm(
                    isEditing = formUiState.isEditing,
                    fileViewModel = fileFormViewmodel
                )
            }) {
                fileFormViewmodel.handleDismissForm(formUiState.isEditing)
            }
        }
    }
}
    */
