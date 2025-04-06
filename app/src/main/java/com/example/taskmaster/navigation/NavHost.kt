package com.example.taskmaster.navigation

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
import com.example.taskmaster.ui.screen.SplashScreen
import com.example.taskmaster.ui.screen.auth.Login
import com.example.taskmaster.ui.screen.projects.Projects.MainScreen
import com.example.taskmaster.ui.viewModel.auth.AuthViewModel
import kotlinx.coroutines.delay

object NavHost {

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun AppNavHost(
        navController: NavHostController,
        authViewModel: AuthViewModel = hiltViewModel(),
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
                    )
                }

                composable(AppScreen.Login.route) {
                    Login.LoginScreen(navController, authViewModel)
                }

            }
        }
    }
}