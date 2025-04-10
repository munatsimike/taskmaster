package com.example.taskmaster.ui.screen.auth

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.taskmaster.R
import com.example.taskmaster.navigation.AppScreen
import com.example.taskmaster.ui.common.state.AuthUiState
import com.example.taskmaster.ui.theme.gradientEnd
import com.example.taskmaster.ui.theme.gradientStart
import com.example.taskmaster.ui.viewModel.auth.AuthViewModel
import com.example.taskmaster.util.components.CustomCard
import com.example.taskmaster.util.components.DisplayImageVectorIcon
import com.example.taskmaster.util.components.ErrorContent
import com.example.taskmaster.util.components.factory.ButtonFactory.PrimaryButton
import com.example.taskmaster.util.components.factory.TextFieldFactory.FilledTextField

/**
* Login object contains composable functions that define the login screen UI.
*
* The screen interacts with the user by taking input for username and password,
* displaying relevant error messages, and showing a login button to initiate
* the login process. It observes the state from the AuthViewModel to update the UI
* based on user interactions and login outcomes.
*/
object Login {

    @Composable
    fun LoginScreen(
        navController: NavController,
        authViewModel: AuthViewModel,
    ) {
        val state by authViewModel.uiState.collectAsState()
        val isPassWordVisible by authViewModel.isPasswordVisible.collectAsState()

        LaunchedEffect(state.hasToken) {
            if (state.hasToken) {
                navController.navigate(AppScreen.Projects.route) {
                    popUpTo(AppScreen.Login.route) { inclusive = true }
                }
            }
        }

        LoginScreenContent(
            uiState = state,
            isPassVisible = isPassWordVisible,
            onButtonClick = authViewModel::login,
            onPasswordChange = authViewModel::onPasswordChange,
            onUsernameChange = authViewModel::onUsernameChange,
            onPasswordToggle = authViewModel::togglePasswordVisibility
        )
    }
}

@Composable
private fun LoginScreenContent(
    isPassVisible: Boolean,
    uiState: AuthUiState,
    onPasswordChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordToggle: () -> Unit,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(gradientEnd, gradientStart),
                    startY = 0.0f
                )
            ), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomCard(
            modifier = Modifier
                .animateContentSize()
                .padding(30.dp),
            cardBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        ) {
            Column(
                modifier = Modifier
                    .padding(30.dp)
                    .background(Color.Transparent, shape = RoundedCornerShape(10.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.taskmaster),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    DisplayImageVectorIcon(
                        icon = ImageVector.vectorResource(R.drawable.user_account_login_icon),
                        tint = MaterialTheme.colorScheme.primary,
                        iconSize = 60
                    )

                    Text(
                        text = stringResource(id = R.string.registered_users),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }


                uiState.error?.let {
                    ErrorContent(message = it, fontSize = 16)
                }

                FilledTextField(
                    value = uiState.username,
                    label = stringResource(id = R.string.username),
                    onValueChange = { onUsernameChange(it) },
                )

                FilledTextField(
                    value = uiState.password,
                    onValueChange = { onPasswordChange(it) },
                    label = stringResource(id = R.string.Password),
                    isPassword = true,
                    isPasswordVisible = isPassVisible,
                    onPasswordToggle = { onPasswordToggle() }
                )
                Spacer(modifier = Modifier.height(10.dp))
                PrimaryButton(
                    onButtonClick = onButtonClick,
                    buttonText = stringResource(id = R.string.login),
                    width = 0.9f
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
