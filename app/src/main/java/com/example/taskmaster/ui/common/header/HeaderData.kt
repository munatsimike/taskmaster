package com.example.taskmaster.ui.common.header

import com.example.taskmaster.domain.LoggedInUser

data class HeaderData (val loggedInUser: LoggedInUser, val projectTitle: String, val currentPage: String, val showBackButton: Boolean = true)