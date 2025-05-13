package com.teqie.taskmaster.ui.model

sealed class ScreenState{
     data object Loading : ScreenState()
     data class Error(val message: String) : ScreenState()
}