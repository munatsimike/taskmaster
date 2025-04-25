package com.teqie.taskmaster.domain

data class LoggedInUser(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val isSuperUser: Boolean = false,
    val avatar: String = ""
)