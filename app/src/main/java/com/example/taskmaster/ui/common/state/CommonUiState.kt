package com.example.taskmaster.ui.common.state

import com.example.taskmaster.ui.model.UiMessage

data class CommonUiState(
    val itemToDelete: String = "",
    val showToolTip: Boolean = false,
    var triggerFetch: Int = 0,
    val selectedIndex: Int = 0,
    val isLoading: Boolean = false,
    val isFABVisible: Boolean = false,
    val deleteDialogState: DeleteDialogState = DeleteDialogState(),
    val isSuccessful: Boolean = false,
    val bottomSheetState:BottomSheetModalState = BottomSheetModalState(),
    override val isDropDownExpanded: Boolean = false,
    override val message: UiMessage = UiMessage(),
    val selectedItemIds: List<String> = emptyList(),
) : UiState(isDropDownExpanded, message)