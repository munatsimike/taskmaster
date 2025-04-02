package com.example.taskmaster.ui.screen.projects

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.taskmaster.ui.viewModel.auth.AuthViewModel

object Projects {
    @Composable
    fun MainScreen(
        navController: NavController,
        authViewModel: AuthViewModel,
        ) {
        val onLogOutClick = {authViewModel.logout()}
        // Collect state from the ViewModel using collectAsState
        val loginUiState by authViewModel.uiState.collectAsState()

        HomeScreen(onLogout = onLogOutClick)
    }

    @Composable
    private fun HomeScreen(
        onLogout: ()-> Unit
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column {
                Text(text = "Welcome to the project Screen")

                Button(
                    onClick = { onLogout.invoke() },
                    content = { Text(text = "Logout") }
                )
            }
        }
    }
}

