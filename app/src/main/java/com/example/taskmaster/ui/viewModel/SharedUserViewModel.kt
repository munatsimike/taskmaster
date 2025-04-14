package com.example.taskmaster.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmaster.domain.LoggedInUser
import com.example.taskmaster.domain.useCases.auth.GetLoggedInUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedUserViewModel @Inject constructor(private val getLoggedInUserUseCase: GetLoggedInUserUseCase,): ViewModel(){

    private val _loggedInUser: MutableStateFlow<LoggedInUser> = MutableStateFlow(LoggedInUser())
    val loggedInUser: StateFlow<LoggedInUser> = _loggedInUser

    init {
        getLoggedInUser()
    }

    private fun getLoggedInUser(){
        viewModelScope.launch {
            getLoggedInUserUseCase().collectLatest { user ->
                _loggedInUser.value = user
            }
        }
    }
}