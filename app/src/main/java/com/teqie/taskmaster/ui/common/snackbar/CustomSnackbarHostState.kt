package com.teqie.taskmaster.ui.common.snackbar

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult

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