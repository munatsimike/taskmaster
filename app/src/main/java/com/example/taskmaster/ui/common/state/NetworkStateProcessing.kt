package com.example.taskmaster.ui.common.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.taskmaster.R
import com.example.taskmaster.data.remote.api.NetworkResponse
import com.example.taskmaster.ui.common.DisplayProgressBar

@Composable
fun <T> ProcessNetworkState(
    state: NetworkResponse<List<T>>,
    progressBarText: String = "",
    onInvalidCredentials: () -> Unit = {},
    fabVisibility: (Boolean) -> Unit = {},
    onErrorFailure: @Composable (String) -> Unit = {},
    content: @Composable (List<T>) -> Unit,

    ) {

    when (state) {
        is NetworkResponse.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                DisplayProgressBar(
                    message = progressBarText,
                    isAnimating = progressBarText.isNotBlank(),
                )
            }
            fabVisibility(false)
        }

        is NetworkResponse.Error -> {
            val message =
                state.exception.localizedMessage ?: stringResource(id = R.string.unexpected_error)

            if (message.contains(stringResource(id = R.string.unable_to_resolve_host)) || message.contains(
                    stringResource(id = R.string.failed_to_connect_to_server)
                )
            ) {
                onErrorFailure(stringResource(id = R.string.no_connection))
            } else {
                // Display error message
                onErrorFailure(message)
            }
            fabVisibility(false)
        }

        is NetworkResponse.Failure -> {
            if (state.code == 401) {
                onInvalidCredentials()
            } else {
                // Display failure message based on the response
                val failureMsg = "Error ${state.code}: ${state.message}"
                onErrorFailure(failureMsg)
            }
            fabVisibility(false)
        }

        is NetworkResponse.Success -> {
            content(state.data) // Pass the successful data to the content composable
            fabVisibility(true)
        }
    }
}
