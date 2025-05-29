package com.teqie.taskmaster.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun BottomSheetModal(
    showModel: Boolean,
    onDismissRequest: () -> Unit,
    sheetContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val state = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(showModel) {
        coroutineScope.launch {
            if (showModel) {
                state.show()
            }
        }
    }

    // Observe the state and update showModel when hidden
    LaunchedEffect(state.isVisible) {
        if (!state.isVisible && showModel) {
            onDismissRequest()
        }
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
        sheetState = state,
        sheetContent = {
            sheetContent()
        }
    ) {
        content()
    }
}