package com.teqie.taskmaster.data.remote.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto (val username: String, val password: String)