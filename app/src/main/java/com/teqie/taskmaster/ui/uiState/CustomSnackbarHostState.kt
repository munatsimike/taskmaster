package com.teqie.taskmaster.ui.uiState

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import com.teqie.taskmaster.ui.model.CustomSnackbarData

class CustomSnackbarHostState {
    private val snackbarHostState = SnackbarHostState()

    var currentSnackbarData: CustomSnackbarData? = null
        private set

    suspend fun showSnackbar(data: CustomSnackbarData): SnackbarResult {
        currentSnackbarData = data
        return snackbarHostState.showSnackbar(data.message)
    }

    fun asSnackbarHostState(): SnackbarHostState = snackbarHostState
}