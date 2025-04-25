package com.teqie.taskmaster.navigation

import com.teqie.taskmaster.ui.constants.Constants.BUDGET_ID
import com.teqie.taskmaster.ui.constants.Constants.BUDGET_PHASE
import com.teqie.taskmaster.ui.constants.Constants.FOLDER_ID
import com.teqie.taskmaster.ui.constants.Constants.INVOICE_ID
import com.teqie.taskmaster.ui.constants.Constants.ORFI_ID


sealed class AppScreen(val route: String, val title: String) {
    data object SplashScreen : AppScreen("splashScreen", "SplashScreen")

    data object Login : AppScreen("login", "Login")

    data object Projects : AppScreen("projects", "Projects")

    data object Budget : AppScreen("budgetPhase", "BudgetPhase")

    data object Schedule : AppScreen("schedule", "Schedules")

    data object Teams : AppScreen("teams", "Teams")

    data object Gallery : AppScreen("gallery/{${FOLDER_ID}}", "Gallery") {
        fun createRoute(folderId: String) = "gallery/$folderId"
    }

    data object ImageDetails : AppScreen("imageDetails", "ImageDetails")

    data object Folders : AppScreen("folders", "Folders")

    data object ORFI : AppScreen("orfi", "ORFI")

    data object ORFIFile : AppScreen("orfiFile/{${ORFI_ID}}", "ORFI Files") {
        fun createRoute(orfiId: String) = "orfiFile/$orfiId"
    }

    data object Dashboard : AppScreen("dashboard", "Dashboard")

    data object BudgetInvoices : AppScreen("invoices/{${BUDGET_ID}}/{${BUDGET_PHASE}}", "Invoices") {
        fun createRoute(budgetId: String, budgetPhase: String) = "invoices/$budgetId/$budgetPhase"
    }

    data object InvoicesFile : AppScreen("invoiceFiles/{$INVOICE_ID}", "Invoices Files") {
        fun createRoute(invoiceId: String) = "invoiceFiles/$invoiceId"
    }
}