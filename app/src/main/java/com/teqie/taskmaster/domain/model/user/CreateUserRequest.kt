package com.teqie.taskmaster.domain.model.user

data class CreateUserRequest(
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val projectId: String = "",
    val role: String = "",
    val username: String = ""
) {
    fun isValid(): Boolean {
        return email.isNotBlank() && name.isNotBlank() && password.isNotBlank() && role.isNotBlank() && username.isNotBlank() && projectId.isNotBlank()
    }
}