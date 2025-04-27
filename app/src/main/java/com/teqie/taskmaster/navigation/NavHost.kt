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
import com.teqie.taskmaster.ui.common.snackbar.CustomSnackbarHostState
import com.teqie.taskmaster.ui.screen.SplashScreen
import com.teqie.taskmaster.ui.screen.auth.Login
import com.teqie.taskmaster.ui.screen.dashboard.Dashboard
import com.teqie.taskmaster.ui.screen.projects.Projects.MainScreen
import com.teqie.taskmaster.ui.viewModel.SharedUserViewModel
import com.teqie.taskmaster.ui.viewModel.SharedViewModel
import com.teqie.taskmaster.ui.viewModel.auth.AuthViewModel
import com.teqie.taskmaster.ui.viewModel.budgetPhase.BudgetViewModel
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
            }
        }
    }
}