package com.teqie.taskmaster.ui.uiState

//this class holds the state of the login UI
data class AuthUiState(
    val username: String = "",       // Holds the current input value of the username field.
    val password: String = "",       // Holds the current input value of the password field.
    val isSuccessful: Boolean = false, // Indicates if the login attempt has been successful.
    val hasToken: Boolean = false,   // indicate if user is logged in
    val error: String? = null,       // Stores an error message in case the login attempt fails.
)
