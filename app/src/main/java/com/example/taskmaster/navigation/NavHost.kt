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
import com.example.taskmaster.ui.screen.auth.Login
import com.example.taskmaster.ui.screen.projects.Projects.MainScreen
import com.example.taskmaster.ui.viewModel.auth.AuthViewModel


object NavHost {

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    fun AppNavHost(
        navController: NavHostController,
        authViewModel: AuthViewModel = hiltViewModel(),
    ) {
        val loginUiState by authViewModel.uiState.collectAsState()

        // Observe token state at the top level
        LaunchedEffect(loginUiState.hasToken) {
            if (!loginUiState.hasToken) {
                navController.navigate(AppScreen.Login.route) {
                    popUpTo(AppScreen.Projects.route) { inclusive = true }
                }
            }
        }
        SharedTransitionLayout {
            NavHost(navController = navController, startDestination = AppScreen.Projects.route) {

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