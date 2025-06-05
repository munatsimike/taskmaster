package com.teqie.taskmaster.ui.components.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.teqie.taskmaster.R
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.ui.components.DisplayProgressBar

@Composable
fun <T> ProcessNetworkState(
    state: Resource<List<T>>,
    progressBarText: String = "",
    onInvalidCredentials: () -> Unit = {},
    fabVisibility: (Boolean) -> Unit = {},
    onErrorFailure: @Composable (String) -> Unit = {},
    content: @Composable (List<T>) -> Unit,
    ) {

    when (state) {
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                DisplayProgressBar(
                    message = progressBarText,
                    isAnimating = progressBarText.isNotBlank(),
                )
            }
            fabVisibility(false)
        }

        is Resource.Error -> {
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

        is Resource.Failure -> {
            if (state.code == 401) {
                onInvalidCredentials()
            } else {
                // Display failure message based on the response
                val failureMsg = "Error ${state.code}: ${state.message}"
                onErrorFailure(failureMsg)
                fabVisibility(true)
            }
        }

        is Resource.Success -> {
            content(state.data) // Pass the successful data to the content composable
            fabVisibility(true)
        }
    }
}
