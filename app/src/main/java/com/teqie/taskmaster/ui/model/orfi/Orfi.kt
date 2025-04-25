package com.teqie.taskmaster.ui.model.orfi

import com.teqie.taskmaster.util.isoStringToLocalDate
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class Orfi(
    val assignedAvatar: String? = null,  // Depending on use case, you might want to handle avatar differently
    val assignedName: String? = null,
    val assignedTo: String? = null,
    val assignedUserName: String? = null,
    val createdAt: String = "",  // Consider using a Date type if appropriate
    val dueDate: String = "",    // Consider using a Date type if appropriate
    val id: String = "",
    val isDeleted: Boolean = false, //
    val projectId: String = "",
    val question: String = "",
    val resolved: Boolean = false,  // Changed to Boolean for clarity
    val updatedAt: String = ""
) {
    fun getRemainingDays(): Long {
        val parsedDueDate = dueDate.isoStringToLocalDate() // Use the extension function
        return ChronoUnit.DAYS.between(LocalDate.now(), parsedDueDate).coerceAtLeast(0)
    }
}
