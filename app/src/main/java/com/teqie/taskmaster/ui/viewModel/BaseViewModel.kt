package com.teqie.taskmaster.ui.viewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel : ProjectViewModel() {

    private val _selectedMenuItem = MutableStateFlow<String?>(null)
    val selectedMenuItem: StateFlow<String?> = _selectedMenuItem

    fun showConfirmDeleteDialog() {
        _screenState.update { it.copy(deleteDialogState = it.deleteDialogState.copy(isVisible = true)) }
    }

    fun selectedItem(name: String) {
        _screenState.update { it.copy(deleteDialogState = it.deleteDialogState.copy(selectedItem = name)) }
    }

    fun selectedItemId(selectedItemId: String) {
        _screenState.update { it.copy(deleteDialogState = it.deleteDialogState.copy(selectedItemId = selectedItemId)) }
    }

    fun hideConfirmDeleteDialog() {
        _screenState.update { it.copy(deleteDialogState = it.deleteDialogState.copy(isVisible = false)) }
    }

    fun onSelectedMenuItem(selectedMenuItemId: String) {
        if (selectedMenuItemId == _selectedMenuItem.value) {
            _selectedMenuItem.value = null
        } else {
            _selectedMenuItem.value = selectedMenuItemId
        }
    }

    fun setFBVisibility(isVisible: Boolean) {
        _screenState.update { it.copy(isFABVisible = isVisible) }
    }

    fun handleDeleteItem(item: String, itemId: String?) {
        if (itemId != null) {
            selectedItem(item)
            selectedItemId(itemId)
            showConfirmDeleteDialog()
        }
    }
}