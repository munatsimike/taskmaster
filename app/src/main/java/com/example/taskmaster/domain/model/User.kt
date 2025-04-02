package com.example.taskmaster.domain.model

data class User(
    val avatar: String = "",
    val name: String = "",
    val isSuperUser: Int = 0,
    val token: String = "",
)