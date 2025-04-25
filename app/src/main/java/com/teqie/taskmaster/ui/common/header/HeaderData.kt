package com.teqie.taskmaster.ui.common.header

import com.teqie.taskmaster.domain.LoggedInUser

data class HeaderData (val loggedInUser: LoggedInUser, val projectTitle: String, val currentPage: String, val showBackButton: Boolean = true)