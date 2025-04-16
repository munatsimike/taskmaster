package com.example.taskmaster.domain.model.budget

import com.example.taskmaster.ui.model.BudgetFormError
import com.example.taskmaster.util.toIsoString
import java.time.LocalDate

data class BudgetPhaseFormData(
    val id: String = "",
    val phase: String = "",
    val progress: String = "",
    val projectId: String = "",
    val totalDuration: String = "",
    val totalAmount: String ="",
    val totalPaid: String = "",
    val initBudget: String = "",
    val assignedToName: String = "",
    val userId: String = "",
    val revisedBudget: String = "",
    val startDate: String = LocalDate.now().toIsoString()
) {

    fun validateBudgetForm(): List<BudgetFormError> {
        val errors = mutableListOf<BudgetFormError>()

        if (phase.isBlank()) errors.add(BudgetFormError.EmptyPhase)
        if (initBudget.isBlank()) errors.add(BudgetFormError.EmptyBudget)
        if (userId.isBlank()) errors.add(BudgetFormError.EmptyUser)

        val durationValue = totalDuration.trim().toIntOrNull()
        if (durationValue == null || durationValue < 0) {
            errors.add(BudgetFormError.InvalidTotalDuration)
        }

        val progressValue = progress.trim().toDoubleOrNull()
        if (progressValue == null || progressValue < 0) {
            errors.add(BudgetFormError.InvalidProgress)
        }

        return errors
    }
}