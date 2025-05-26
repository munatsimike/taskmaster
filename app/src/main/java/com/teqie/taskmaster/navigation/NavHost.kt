package com.teqie.taskmaster.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.teqie.taskmaster.ui.screen.orfi.ORFI
import com.teqie.taskmaster.ui.screen.orfi.ORFIFiles
import com.teqie.taskmaster.ui.components.snackbar.CustomSnackbarHostState
import com.teqie.taskmaster.ui.constants.Constants.BUDGET_ID
import com.teqie.taskmaster.ui.constants.Constants.BUDGET_PHASE
import com.teqie.taskmaster.ui.constants.Constants.FOLDER_ID
import com.teqie.taskmaster.ui.constants.Constants.INVOICE_ID
import com.teqie.taskmaster.ui.constants.Constants.ORFI_ID
import com.teqie.taskmaster.ui.screen.SplashScreen
import com.teqie.taskmaster.ui.screen.auth.Login
import com.teqie.taskmaster.ui.screen.bugdetPhase.BudgetPhase
import com.teqie.taskmaster.ui.screen.bugdetPhase.InvoiceFile
import com.teqie.taskmaster.ui.screen.bugdetPhase.Invoices
import com.teqie.taskmaster.ui.screen.dashboard.Dashboard
import com.teqie.taskmaster.ui.screen.gallery.Folders
import com.teqie.taskmaster.ui.screen.gallery.Gallery
import com.teqie.taskmaster.ui.screen.gallery.ImageDetails
import com.teqie.taskmaster.ui.screen.projects.Projects.MainScreen
import com.teqie.taskmaster.ui.screen.schedule.Schedules
import com.teqie.taskmaster.ui.screen.teams.Teams
import com.teqie.taskmaster.ui.viewModel.SharedUserViewModel
import com.teqie.taskmaster.ui.viewModel.SharedViewModel
import com.teqie.taskmaster.ui.viewModel.auth.AuthViewModel
import com.teqie.taskmaster.ui.viewModel.budgetPhase.BudgetViewModel
import com.teqie.taskmaster.ui.viewModel.file.FileFormManagementViewModel
import com.teqie.taskmaster.ui.viewModel.file.FileManagementViewModel
import com.teqie.taskmaster.ui.viewModel.gallery.GalleryFormViewModel
import com.teqie.taskmaster.ui.viewModel.gallery.GalleryViewModel
import com.teqie.taskmaster.ui.viewModel.invoice.InvoiceViewModel
import kotlinx.coroutines.delay

object NavHost {

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun AppNavHost(
        navController: NavHostController,
        authViewModel: AuthViewModel = hiltViewModel(),
        sharedViewModel: SharedViewModel = hiltViewModel(),
        snackbarHostState: CustomSnackbarHostState,
        sharedUserViewModel: SharedUserViewModel = hiltViewModel(),
        budgetViewModel: BudgetViewModel = hiltViewModel(),
        invoiceViewModel: InvoiceViewModel = hiltViewModel(),
        fileManagementViewModel: FileManagementViewModel = hiltViewModel(),
        galleryFormViewModel: GalleryFormViewModel = hiltViewModel(),
        galleryViewModel: GalleryViewModel = hiltViewModel(),
        fileFormManagementViewModel: FileFormManagementViewModel  = hiltViewModel()
    ) {
        val loginUiState by authViewModel.uiState.collectAsState()

        SharedTransitionLayout {
            NavHost(
                navController = navController,
                startDestination = AppScreen.SplashScreen.route
            ) {
                composable(AppScreen.SplashScreen.route) {

                    // Observe token
                    LaunchedEffect(loginUiState.hasToken) {
                        delay(3000)
                        if (loginUiState.hasToken) {
                            navController.navigate(AppScreen.Projects.route) {
                                popUpTo(AppScreen.SplashScreen.route) { inclusive = true }
                            }
                        } else {
                            navController.navigate(AppScreen.Login.route) {
                                popUpTo(AppScreen.SplashScreen.route) { inclusive = true }
                            }
                        }
                    }

                    SplashScreen.MainScreen()
                }

                composable(AppScreen.Projects.route) {
                    MainScreen(
                        navController = navController,
                        authViewModel = authViewModel,
                        sharedUserViewModel = sharedUserViewModel,
                        sharedViewModel = sharedViewModel,
                        snackBarHostState = snackbarHostState,
                    )
                }

                composable(AppScreen.Login.route) {
                    Login.LoginScreen(navController, authViewModel)
                }

                composable(
                    AppScreen.Dashboard.route
                ) {
                    Dashboard.MainScreen(
                        navController,
                        sharedViewModel,
                        sharedUserViewModel
                    )
                }

                composable(AppScreen.Budget.route) {
                    BudgetPhase.BudgetPhaseMainScreen(
                        navController = navController,
                        sharedViewModel,
                        sharedUserViewModel,
                        authViewModel,
                        snackbarHostState,
                        budgetPhaseViewModel = budgetViewModel,
                    )
                }

                composable(AppScreen.Teams.route) {
                    Teams.TeamsMainScreen(
                        sharedViewModel,
                        navController,
                        authViewModel,
                        snackbarHostState,
                        sharedUserViewModel
                    )
                }

                composable(AppScreen.ORFI.route) {
                    ORFI.ORFIMainScreen(
                        navController,
                        sharedViewModel,
                        sharedUserViewModel,
                        authViewModel,
                        snackbarHostState

                    )
                }

                composable(AppScreen.ORFIFile.route) { backStack ->
                    val orfiId = backStack.arguments?.getString(ORFI_ID)
                    if (orfiId != null) {
                        ORFIFiles.ORFIFILEMainScreen(
                            orfiId = orfiId,
                            navController = navController,
                            sharedViewModel = sharedViewModel,
                            authViewModel = authViewModel,
                            sharedUserViewModel = sharedUserViewModel,
                            fileFormViewModel = fileFormManagementViewModel,
                            fileViewModel = fileManagementViewModel,
                            snackbarHostState = snackbarHostState
                        )
                    }
                }


                composable(AppScreen.Schedule.route) {
                    Schedules.SchedulesMainScreen(
                        navController,
                        sharedUserViewModel,
                        sharedViewModel,
                        snackbarHostState
                    )
                }

                composable(AppScreen.BudgetInvoices.route) { backStack ->
                    val budgetId = backStack.arguments?.getString(BUDGET_ID)
                    val budgetPhase = backStack.arguments?.getString(BUDGET_PHASE)
                    if (budgetId != null && budgetPhase != null) {
                        Invoices.InvoiceScreen(
                            budgetId,
                            budgetPhase,
                            navController,
                            sharedViewModel,
                            sharedUserViewModel,
                            authViewModel,
                            snackbarHostState,
                            invoiceViewModel =invoiceViewModel,
                        )
                    }
                }

                composable(AppScreen.InvoicesFile.route) { backStack ->
                    val invoiceId = backStack.arguments?.getString(INVOICE_ID)
                    if (invoiceId != null) {
                        InvoiceFile.InvoiceFileMainScreen(
                            invoiceId,
                            navController,
                            sharedViewModel,
                            sharedUserViewModel,
                            authViewModel,
                            snackbarHostState = snackbarHostState,
                            invoiceViewModel = invoiceViewModel,
                            fileViewModel = fileManagementViewModel,
                            fileFormViewmodel = fileFormManagementViewModel
                        )
                    }
                }

                composable(AppScreen.Gallery.route) { backStack ->
                    val folderId = backStack.arguments?.getString(FOLDER_ID)
                    if (folderId != null) {
                        Gallery.GalleryMainScreen(
                            folderId = folderId,
                            navController = navController,
                            sharedViewModel = sharedViewModel,
                            sharedUserViewModel,
                            authViewModel,
                            animatedVisibilityScope = this,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            galleryViewModel = galleryViewModel,
                            snackBarHostState = snackbarHostState,
                            fileViewModel = fileFormManagementViewModel
                        )
                    }
                }

                composable(AppScreen.ImageDetails.route) {
                    ImageDetails.MaingScreen(
                        navController = navController,
                        animatedVisibilityScope = this,
                        galleryViewModel = galleryViewModel,
                        sharedTransitionScop =  this@SharedTransitionLayout,
                        sharedViewModel = sharedViewModel,
                        fileManagementViewModel = fileManagementViewModel
                    )
                }

                composable(AppScreen.Folders.route) {
                    Folders.FoldersMainScreen(
                        sharedViewModel = sharedViewModel,
                        navController,
                        sharedUserViewModel,
                        snackbarHostState,
                        galleryFormViewModel = galleryFormViewModel,
                        galleryViewModel = galleryViewModel
                    )
                }

            }
        }
    }
}