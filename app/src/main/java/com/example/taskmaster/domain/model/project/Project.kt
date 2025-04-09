package com.example.taskmaster.domain.model.project

data class Project(
    val id: String = "",
    val name: String = "",
    val isEditing: Boolean = false,
    val description: String = "",
    val address: String? = null,
    val thumbnailUrl: String? = null,
    val isDeleted: Boolean = false,
    val createdAt: String = "",
    val updatedAt: String? = null
) {
    fun isValid(): Boolean {
        if (name.isBlank() || name.length < 3) return false
        if (description.isBlank() || description.length < 5) return false

        return true
    }
}